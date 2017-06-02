package de.chrthms.bpm.iot.services.impl;

import de.chrthms.bpm.iot.exceptions.MicroMqttRuntimeException;
import de.chrthms.bpm.iot.enums.MqttQoS;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by christian on 28.05.17.
 */
public class MicroMqttCommand {

    private enum CommandType {
        NOT_SET,
        PUBLISH_STRING_MSG,
        SUBSCRIBE
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
    private String message = null;

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

    public void execute() throws MicroMqttRuntimeException {
        switch (commandType) {
            case NOT_SET:
                throw new MicroMqttRuntimeException("Not specified, what to do (publish, subscribe, ..)");
            case SUBSCRIBE:
                subscribe();
                break;
            case PUBLISH_STRING_MSG:
                publishStringMessage();
                break;
            default:
                throw new MicroMqttRuntimeException("Unknown command type = " + commandType);
        }
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

    private void subscribe() throws MicroMqttRuntimeException {
        MqttClient client = createConnectedClient();
        checkTopic();

    }

}
