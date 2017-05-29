package de.chrthms.iot.services;

import de.chrthms.iot.services.impl.MicroMqttCommand;
import de.chrthms.iot.enums.MqttQoS;
import de.chrthms.iot.exceptions.MicroMqttRuntimeException;

/**
 * Created by christian on 28.05.17.
 */
public interface MicroMqttService {

    /**
     * Describes the broker location. For example:
     * "tcp://broker.mqttdashboard.com" or
     * "tcp://iot.eclipse.org:1883"
     *
     * @param brokerUrl must be set for establish a connection.
     * @return the factory
     */
    MicroMqttService defaultBroker(String brokerUrl);

    /**
     * Credentials for authentication
     *
     * @param username
     * @return
     */
    MicroMqttService defaultUsername(String username);

    /**
     * Credentials for authentication
     *
     * Only recognized, if username is set!
     *
     * @param password
     * @return
     */
    MicroMqttService defaultPassword(String password);

    /**
     *
     * Set the Quality of Service
     *
     * A good explanation
     * http://www.hivemq.com/blog/mqtt-essentials-part-6-mqtt-quality-of-service-levels
     *
     * @param qos "Exactly once (2)" per default
     * @return the factory
     */
    MicroMqttService defaultQoS(MqttQoS qos);

    /**
     * A good explanation
     * http://www.hivemq.com/blog/mqtt-essentials-part-8-retained-messages
     * @param retained false per default
     * @return the factory
     */
    MicroMqttService defaultRetained(Boolean retained);

    MicroMqttService defaultAutoReconnect(Boolean reconnect);

    /**
     * Specifies a client-id.
     * @param clientId per default an id will be generated, if not set.
     * @return the factory
     */
    MicroMqttService mqttClientId(String clientId);

    MicroMqttCommand createMqttCommand() throws MicroMqttRuntimeException;

}
