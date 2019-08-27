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

    @PostMapping(path = "/getId", consumes = "application/json", produces = "application/json")
    public HashMap<String, Object> login(@RequestBody User user) {
        int id;

        HashMap <String, Object> response = new HashMap<String, Object>();
        id = userRepository.getUserId(user);
        //Got the id.  I want to return a flexible Map to return an error or user info.
        if (id < 1) {
            response.put("data","Invalid login, please try again or register");
        } else {
            response.put("data", user);
            //response.setResponse(Integer.toString(id));

        }
        return (response);
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Message testit(@RequestBody User user) {
        return (userRepository.createUser(user));
    }
}
