package IoT.IoTSmartLamp.mqttwebapp;

import IoT.IoTSmartLamp.mqttwebapp.MqttPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private MqttPublisher mqttPublisher;

    @GetMapping("/")
    public String index() {
        return "index";  // Loads index.html
    }

    @GetMapping("/send/on")
    public String sendOn() {
        mqttPublisher.sendMessage("ON");
        return "redirect:/";  // Reloads page
    }

    @GetMapping("/send/off")
    public String sendOff() {
        mqttPublisher.sendMessage("OFF");
        return "redirect:/";  // Reloads page
    }
}
