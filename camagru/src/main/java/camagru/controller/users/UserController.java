package camagru.controller.users;

import camagru.Message;
import camagru.User;
import camagru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/users", produces="application/json")
@CrossOrigin(origins="*")
public class UserController {
    private User user;
    private Message response;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUser")
    public User getUser(@RequestParam("id") int id) {
        this.user = userRepository.getUser(id);
        return (this.user);
    }

    @GetMapping("/getAllUsernames")
    public List<String> getAllUsers() {
        return (userRepository.getAllUserNames());
    }

    @GetMapping("/getAll")
    public List<Map<String, Object>> getAll() {
        return (userRepository.getAll());
    }

    @PostMapping("/create")
    public Message createUser (@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
            this.user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);
            user.setActive(0);
            user.setReceiveNotifications(1);
            return (userRepository.createUser(user));
    }


//
/*    @GetMapping("/create")
    public Message createUser(@RequestParam Map<String, String> req) {
        this.user = new User(req.get("email"), req.get("username"), req.get("password"), (byte)0, (byte)1);
        this.response = new Message();
        this.response.setResponse("User created");
        return (this.response);
    }
 */
}
