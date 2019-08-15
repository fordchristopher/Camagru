package camagru.controller;

import camagru.Message;
import camagru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/users", produces="application/json")
@CrossOrigin(origins="*")
public class UserController {
    private User user;
    private Message response;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User getUser() {
        this.user = new User("email", "username", "password", (byte)0, (byte)1);
        return (this.user);
    }

    @GetMapping("/getAll")
    public List<String> getAllUsers() {
        return (userRepository.getAllUserNames());
    }

/*    @GetMapping("/create")
    public Message createUser(@RequestParam Map<String, String> req) {
        this.user = new User(req.get("email"), req.get("username"), req.get("password"), (byte)0, (byte)1);
        this.response = new Message();
        this.response.setResponse("User created");
        return (this.response);
    }
 */
 //   @PostMapping("/create")
  //  public Message createUser
}
