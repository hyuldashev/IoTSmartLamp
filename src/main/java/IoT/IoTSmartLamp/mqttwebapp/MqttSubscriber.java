package IoT.IoTSmartLamp.mqttwebapp;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {

    private static final String BROKER = "tcp://34.88.101.222:1883"; // or use your local broker
    private static final String TOPIC = "ttpu/iot";

    private String latestMessage = "No data yet";

    public MqttSubscriber() {
        try {
            MqttClient client = new MqttClient(BROKER, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);

            client.subscribe(TOPIC, (topic, message) -> {
                String msg = new String(message.getPayload());
                System.out.println("Received: " + msg);
                latestMessage = msg;
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String getLatestMessage() {
        return latestMessage;
    }
}
