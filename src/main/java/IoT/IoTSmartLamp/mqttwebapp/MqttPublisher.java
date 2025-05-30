package IoT.IoTSmartLamp.mqttwebapp;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {

    private static final String BROKER = "tcp://broker.hivemq.com:1883";
    private static final String TOPIC = "esp32/control"; // ESP32 should subscribe to this topic

    private MqttClient client;

    public MqttPublisher() {
        try {
            client = new MqttClient(BROKER, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String payload) {
        try {
            System.out.println("some text   " );
            if (client.isConnected()) {
                MqttMessage message = new MqttMessage(payload.getBytes());
                client.publish(TOPIC, message);
                System.out.println("Sent: " + payload);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
