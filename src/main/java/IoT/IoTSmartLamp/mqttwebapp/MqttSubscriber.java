package IoT.IoTSmartLamp.mqttwebapp;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {

    private static final String BROKER = "tcp://34.88.101.222:1883";

    private static final String TOPIC_RELAY = "ttpu/iot";
    private static final String TOPIC_MODE = "ttpu/mode";
    private static final String TOPIC_PHOTO = "ttpu/photo";
    private static final String TOPIC_PIR = "ttpu/pir";
    private static final String TOPIC_LAMP = "ttpu/lamp";

    private String latestRelay = "Unknown";
    private String latestMode = "Unknown";
    private String latestPhoto = "--";
    private String latestPir = "--";
    private String latestLampState = "OFF";  // Default OFF

    private MqttClient client;

    public MqttSubscriber() {
        try {
            client = new MqttClient(BROKER, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);

            client.subscribe(TOPIC_RELAY, this::messageArrived);
            client.subscribe(TOPIC_MODE, this::messageArrived);
            client.subscribe(TOPIC_PHOTO, this::messageArrived);
            client.subscribe(TOPIC_PIR, this::messageArrived);
            client.subscribe(TOPIC_LAMP, this::messageArrived);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void messageArrived(String topic, MqttMessage message) {
        String msg = new String(message.getPayload());
        System.out.println("Received on " + topic + ": " + msg);

        switch (topic) {
            case TOPIC_RELAY:
                latestRelay = msg;
                break;
            case TOPIC_MODE:
                latestMode = msg;
                break;
            case TOPIC_PHOTO:
                latestPhoto = msg;
                break;
            case TOPIC_PIR:
                latestPir = msg;
                break;
            case TOPIC_LAMP:
                latestLampState = msg;
                break;
        }
    }

    public String getLatestRelay() {
        return latestRelay;
    }

    public String getLatestMode() {
        return latestMode;
    }

    public String getLatestPhoto() {
        return latestPhoto;
    }

    public String getLatestPir() {
        return latestPir;
    }

    public String getLatestLampState() {
        return latestLampState;
    }
}
