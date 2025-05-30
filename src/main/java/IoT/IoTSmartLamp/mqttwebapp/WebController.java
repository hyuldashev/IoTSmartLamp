package IoT.IoTSmartLamp.mqttwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private MqttPublisher mqttPublisher;

    @Autowired
    private MqttSubscriber mqttSubscriber;

    @GetMapping("/")
    public String index() {
        return "index";  // Thymeleaf template index.html
    }

    // Relay commands
    @GetMapping("/send/on")
    public String sendOn() {
        mqttPublisher.sendRelayCommand("ON");
        return "redirect:/";
    }

    @GetMapping("/send/off")
    public String sendOff() {
        mqttPublisher.sendRelayCommand("OFF");
        return "redirect:/";
    }

    // Mode command (mode is 1,2,3)
    @GetMapping("/send/mode/{mode}")
    @ResponseBody
    public String sendMode(@PathVariable String mode) {
        mqttPublisher.sendModeCommand(mode);
        return "Mode sent: " + mode;
    }

    // Data endpoint for AJAX sensor and lamp state
    @GetMapping("/data")
    @ResponseBody
    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("relay", mqttSubscriber.getLatestRelay());
        data.put("mode", mqttSubscriber.getLatestMode());
        data.put("photo", mqttSubscriber.getLatestPhoto());
        data.put("pir", mqttSubscriber.getLatestPir());
        data.put("lamp", mqttSubscriber.getLatestLampState());
        return data;
    }
}
