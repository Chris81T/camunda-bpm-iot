package de.chrthms.bpm.iot.services.impl;

import de.chrthms.bpm.iot.exceptions.MicroMqttRuntimeException;
import de.chrthms.bpm.iot.services.enums.MqttQoS;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.eclipse.paho.client.mqttv3.*;

import java.util.logging.Logger;

/**
 * Created by christian on 28.05.17.
 */
public class MicroMqttCommand {

    private static final Logger LOG = Logger.getLogger(MicroMqttCommand.class.getName());

    private enum CommandType {
        NOT_SET,
        PUBLISH_STRING_MSG,
        SUBSCRIBE_CALLBACK_MSG_TO_PROCESS;
    }

    private final MicroMqttServiceImpl mqttService;

    private String brokerUrl = null;
    private boolean autoReconnect = false;
    private String username = null;
    private String password = null;
    private String clientId = null;
    private MqttQoS qos = null;
    private boolean retained = false;

    private CommandType commandType = CommandType.NOT_SET;
    private String topic = null;

    // relevant for publishing a message to a topic
    private String message = null;

    // relevant for subscribing a topic
    private String messageName = null;
    private String processDefinitionKey = null;
    private String varNameForContent = null;

    protected MicroMqttCommand(MicroMqttServiceImpl mqttService, String brokerUrl, boolean autoReconnect, String username,
                               String password, String clientId, MqttQoS qos, boolean retained) {
        this.mqttService = mqttService;
        this.brokerUrl = brokerUrl;
        this.autoReconnect = autoReconnect;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.qos = qos;
        this.retained = retained;
    }

    public MicroMqttCommand brokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
        return this;
    }

    public MicroMqttCommand autoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
        return this;
    }

    public MicroMqttCommand username(String username) {
        this.username = username;
        return this;
    }

    public MicroMqttCommand password(String password) {
        this.password = password;
        return this;
    }

    public MicroMqttCommand clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public MicroMqttCommand qos(MqttQoS qos) {
        this.qos = qos;
        return this;
    }

    public MicroMqttCommand retained(boolean retained) {
        this.retained = retained;
        return this;
    }

    public MicroMqttCommand topic(String topic) {
        this.topic = topic;
        return this;
    }

    public MicroMqttCommand publish(String message) {
        this.message = message;
        commandType = CommandType.PUBLISH_STRING_MSG;
        return this;
    }

    public MicroMqttCommand subscribe(String processDefinitionKey, String messageName, String varNameForContent) {
        this.messageName = messageName;
        this.processDefinitionKey = processDefinitionKey;
        this.varNameForContent = varNameForContent;
        commandType = CommandType.SUBSCRIBE_CALLBACK_MSG_TO_PROCESS;
        return this;
    }

    public void execute() throws MicroMqttRuntimeException {
        switch (commandType) {
            case NOT_SET:
                throw new MicroMqttRuntimeException("Not specified, what to do (publish, subscribeCallbackMsgToProcess, ..)");
            case SUBSCRIBE_CALLBACK_MSG_TO_PROCESS:
                subscribeCallbackMsgToProcess();
                break;
            case PUBLISH_STRING_MSG:
                publishStringMessage();
                break;
            default:
                throw new MicroMqttRuntimeException("Unknown command type = " + commandType);
        }
    }

    @Override
    public String toString() {
        return "MicroMqttCommand{" +
                "mqttService=" + mqttService +
                ", brokerUrl='" + brokerUrl + '\'' +
                ", autoReconnect=" + autoReconnect +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", clientId='" + clientId + '\'' +
                ", qos=" + qos +
                ", retained=" + retained +
                ", commandType=" + commandType +
                ", topic='" + topic + '\'' +
                ", message='" + message + '\'' +
                ", messageName='" + messageName + '\'' +
                ", processDefinitionKey='" + processDefinitionKey + '\'' +
                '}';
    }

    private MqttClient createConnectedClient() throws MicroMqttRuntimeException {
        try {
            MqttClient client = new MqttClient(brokerUrl,
                    (clientId == null || clientId.isEmpty()) ? MqttClient.generateClientId() : clientId);

            MqttConnectOptions options = new MqttConnectOptions();

            options.setAutomaticReconnect(autoReconnect);

            if (username != null && !username.isEmpty()) {
                options.setUserName(username);
                options.setPassword(password.toCharArray());
            }

            client.connect(options);

            return client;
        } catch (MqttException e) {
            throw new MicroMqttRuntimeException("Could not create client instance while MqttException is thrown!", e);
        } catch (Exception e) {
            throw new MicroMqttRuntimeException("Could not create client instance!", e);
        }

    }

    private void checkTopic() {
        if (topic == null || topic.isEmpty()) {
            throw new MicroMqttRuntimeException("No topic is set!");
        }
    }

    private void publishStringMessage() throws MicroMqttRuntimeException {
        MqttClient client = createConnectedClient();
        checkTopic();

        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos.getValue());
            mqttMessage.setRetained(retained);

            client.publish(topic, mqttMessage);
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            throw new MicroMqttRuntimeException("Could not publish string message while MqttException is thrown!", e);
        } catch (Exception e) {
            throw new MicroMqttRuntimeException("Could not publish string message!", e);
        }
    }

    private void subscribeCallbackMsgToProcess() throws MicroMqttRuntimeException {
        MqttClient client = createConnectedClient();
        checkTopic();

        final MicroMqttCommand mqttCommand = this;

        try {

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    LOG.warning("Mqtt connection is lost. Cause = " +
                        cause.getLocalizedMessage() +
                        ", MqttCommand = " +
                        mqttCommand +
                        ". Command will be unregistered!");

                    mqttService.unregisterSubscribedCommand(mqttCommand);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    final String content = new String(message.getPayload());

                    LOG.info(new StringBuilder()
                        .append("Incoming message from topic = ")
                        .append(topic)
                        .append(" with content = ")
                        .append(content)
                        .append(". About to send bpmn message with name ")
                        .append(messageName)
                        .append(" with content as process variable with name = ")
                        .append(varNameForContent)
                        .toString()
                    );

                    MessageCorrelationResult messageCorrelationResult = mqttService.getProcessEngine()
                            .getRuntimeService()
                            .createMessageCorrelation(messageName)
                            .setVariable(varNameForContent, content)
                            .correlateWithResult();

                    switch (messageCorrelationResult.getResultType()) {
                        case Execution:
                            LOG.info(new StringBuilder()
                                .append("Message is send to execution with id = ")
                                .append(messageCorrelationResult.getExecution().getId())
                                .append(", process instance id = ")
                                .append(messageCorrelationResult.getExecution().getProcessInstanceId())
                                .toString());
                            break;
                        case ProcessDefinition:
                            LOG.info(new StringBuilder()
                                    .append("Message is send to process-definition with id = ")
                                    .append(messageCorrelationResult.getProcessInstance().getProcessDefinitionId())
                                    .append(", process instance id = ")
                                    .append(messageCorrelationResult.getProcessInstance().getProcessInstanceId())
                                    .toString());
                            break;
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            LOG.fine("Register this command to hold the instance");
            mqttService.registerSubscribedCommand(this);

            client.subscribe(topic);

        } catch (MqttException e) {
            throw new MicroMqttRuntimeException("Could not subscribe topic while MqttException is thrown!", e);
        } catch (MismatchingMessageCorrelationException e) {
            LOG.warning("Could not send received Mqtt message as bpmn message. No or more " +
                    "than one message receiver detected! Exception message = " +
                    e.getLocalizedMessage());
        } catch (Exception e) {
            throw new MicroMqttRuntimeException("Could not subscribe topic!", e);
        }
    }

}
