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

  /*  @GetMapping("/{id}")
    public User getUser() {
        this.user = new User("email", "username", "password", (byte)0, (byte)1);
        return (this.user);
    }*/
//
    @GetMapping("/getAllUsernames")
    public List<String> getAllUsers() {
        return (userRepository.getAllUserNames());
    }

    @GetMapping("/getAll")
    public List<Map<String, Object>> getAll() {
        return (userRepository.getAll());
    }

    @PostMapping("/create")
    public Message createUser (@RequestParam("Userinfo") CreateUserPost userInfo) {
            this.user = new User();
            user.setEmail(userInfo.getEmail());
            user.setPassword(userInfo.getPassword());
            user.setUsername(userInfo.getUsername());
            user.setActive((byte)0);
            user.setReceiveNotifications((byte)0);
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
