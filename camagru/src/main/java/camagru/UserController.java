package camagru;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/users", produces="application/json")
@CrossOrigin(origins="*")
public class UserController {
    private User user;
    private Message response;
    private MySQLSEL connection;

    @GetMapping("/{id}")
    public User getUser() {
        this.connection = new MySQLSEL();
        this.connection.select("SELECT * FROM `users` WHERE 1;");
        this.user = new User("email", "username", "password", (byte)0, (byte)1);
        return (this.user);
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
