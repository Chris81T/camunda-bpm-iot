package de.chrthms.bpm.iot.services.impl;

import de.chrthms.bpm.iot.MicroProcessEngine;
import de.chrthms.bpm.iot.exceptions.MicroMqttRuntimeException;
import de.chrthms.bpm.iot.services.MicroMqttService;
import de.chrthms.bpm.iot.services.enums.MqttQoS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by christian on 29.05.17.
 */
public class MicroMqttServiceImpl implements MicroMqttService {

    private final MicroProcessEngine processEngine;

    private String defaultBrokerUrl = null;
    private boolean defaultAutoReconnect = false;
    private String defaultUsername = null;
    private String defaultPassword = null;
    private String defaultClientId = null;
    private MqttQoS defaultQoS = MqttQoS.EXACTLY_ONCE;
    private boolean defaultRetained = false;

    private List<MicroMqttCommand> subscribedCommands = new ArrayList<>();

    public MicroMqttServiceImpl(MicroProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public MicroProcessEngine getProcessEngine() {
        return processEngine;
    }

    public void registerSubscribedCommand(MicroMqttCommand command) {
        subscribedCommands.add(command);
    }

    public void unregisterSubscribedCommand(MicroMqttCommand command) {
        subscribedCommands = subscribedCommands.stream()
                .filter(current -> current != command)
                .collect(Collectors.toList());
    }

    @Override
    public MicroMqttService defaultBroker(String brokerUrl) {
        this.defaultBrokerUrl = brokerUrl;
        return this;
    }

    @Override
    public MicroMqttService defaultUsername(String username) {
        this.defaultUsername = username;
        return this;
    }

    @Override
    public MicroMqttService defaultPassword(String password) {
        this.defaultPassword = password;
        return this;
    }

    @Override
    public MicroMqttService defaultQoS(MqttQoS qos) {
        this.defaultQoS = qos;
        return this;
    }

    @Override
    public MicroMqttService defaultRetained(Boolean retained) {
        this.defaultRetained = retained;
        return this;
    }

    @Override
    public MicroMqttService defaultAutoReconnect(Boolean reconnect) {
        this.defaultAutoReconnect = reconnect;
        return this;
    }

    @Override
    public MicroMqttService mqttClientId(String clientId) {
        this.defaultClientId = clientId;
        return this;
    }

    @Override
    public MicroMqttCommand createMqttCommand() throws MicroMqttRuntimeException {
        return new MicroMqttCommand(this, defaultBrokerUrl, defaultAutoReconnect, defaultUsername,
                defaultPassword, defaultClientId, defaultQoS, defaultRetained);
    }

}
