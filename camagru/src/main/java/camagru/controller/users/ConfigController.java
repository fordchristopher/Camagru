package camagru.controller.users;

import camagru.Message;
import camagru.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/config", produces="application/json")
@CrossOrigin(origins="http://localhost:3000")
public class ConfigController {
    Message message = new Message();
    @Autowired
    private ConfigRepository configRepository;

    @GetMapping("/setup")
    public Message setup() {
        try {
            configRepository.setup();
            message.setResponse("Database set up");
        } catch (Exception e) {
            e.printStackTrace();
            message.setResponse("Database set up failed");
        }
        return (message);
    }
}
