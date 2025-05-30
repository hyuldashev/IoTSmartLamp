package IoT.IoTSmartLamp.mqttwebapp;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {

    private static final String BROKER = "tcp://34.88.101.222:1883";

    // Topics
    private static final String TOPIC_RELAY = "ttpu/iot";
    private static final String TOPIC_MODE = "ttpu/mode";

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
            System.out.println("some changes");
        }
    }

    public void sendRelayCommand(String payload) {
        sendMessage(TOPIC_RELAY, payload);
    }

    public void sendModeCommand(String payload) {
        sendMessage(TOPIC_MODE, payload);
    }

    private void sendMessage(String topic, String payload) {
        try {
            if (client.isConnected()) {
                MqttMessage message = new MqttMessage(payload.getBytes());
                client.publish(topic, message);
                System.out.println("Sent to " + topic + ": " + payload);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
