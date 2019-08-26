package camagru.controller.users;

import camagru.Message;
import camagru.User;
import camagru.UserInfo;
import camagru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/users", produces="application/json")
@CrossOrigin(origins="http://localhost:3000")
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
        System.out.println("Into the API!");
        return (userRepository.getAll());
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Message createUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
            this.user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);
            user.setActive(0);
            user.setReceiveNotifications(1);
            System.out.println("Into the API!");
            return (userRepository.createUser(user));
    }

    @PostMapping(path = "/test", consumes = "application/json", produces = "application/json")
    public void testit(@RequestBody UserInfo test) {
        System.out.println("Into the API");
        System.out.println(test.getId());
        test.setId(15);
        System.out.println(test.getId());
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
