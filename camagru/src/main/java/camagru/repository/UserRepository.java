package camagru.repository;

import camagru.EmailContent;
import camagru.MailUtil;
import camagru.Message;
import camagru.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User user;

    public List<String> getAllUserNames() {
        List<String> usernameList = new ArrayList<>();

        usernameList.addAll(jdbcTemplate.queryForList("SELECT USERNAME FROM users;", String.class));
        return (usernameList);
    }

    public List<Map<String,Object>> getAll() {
        return jdbcTemplate.queryForList("SELECT * FROM users;");
    }

    public User getUser(int id)  {
        this.user = new User();
        Map<String, Object> res;
        res = jdbcTemplate.queryForMap("SELECT * FROM users WHERE id = ?", id);
        this.user.setMap(res);
        this.user.setId(id);
        return (this.user);
    }

    public int getUserId(User user) {
        Message msg = new Message();
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT id FROM users WHERE email = ?;", user.getEmail());
        if (res.size() > 0) {
            return ((int) res.get(0).get("id"));
        }
        return (-1);
    }

    public HashMap<String, Object> authenticate(String email, String password) {
        HashMap<String, Object> data;
        List<Map<String, Object>> res;

        data = new HashMap<String, Object>();
        res = jdbcTemplate.queryForList("SELECT * FROM users WHERE email = ? AND password = ?;", email, password);
        if (res.size() > 0) {
            data.put("data", res.get(0));
        } else {
            data.put("data", "Invalid Login");
        }
        return (data);
    }

    public Message updateEmail(User user) {
        Message msg = new Message();
        try {
            jdbcTemplate.update("UPDATE users SET email = ? WHERE id = ?;", user.getEmail(), user.getId());
            msg.setResponse("Email address updated!");
        } catch (Exception e) {
            msg.setResponse("Email address update failed");
        }
        return (msg);
    }

    public Message updateUsername(User user) {
        Message msg = new Message();
        try {
            jdbcTemplate.update("UPDATE users SET username = ? WHERE id = ?;", user.getUsername(), user.getId());
            msg.setResponse("Username updated!");
        } catch (Exception e) {
            msg.setResponse("Username update failed");
        }
        return (msg);
    }

    public Message updatePassword(User user) {
        Message msg = new Message();
        try {
            jdbcTemplate.update("UPDATE users SET password = ? WHERE id = ?;", user.getPassword(), user.getId());
            msg.setResponse("Password updated!");
        } catch (Exception e) {
            msg.setResponse("Password update failed");
        }
        return (msg);
    }

    public Message createUser(User user) {
        Message msg = new Message();
        MailUtil util = new MailUtil();
        EmailContent content = new EmailContent();

        if (this.getUserId(user) > 0) {
            msg.setResponse("Duplicate email address, please enter a new email.");
            return (msg);
        }
        else {
            try {
                content.setBody("Thanks for signing up with us");
                content.setRecipient(user.getEmail());
                content.setSubject("Camagru new account confirmation");
                jdbcTemplate.update("INSERT INTO users (`email`, `username`, `password`) VALUES (?, ?, ?);",
                        user.getEmail(), user.getUsername(), user.getPassword());
                util.sendMail(content);
                msg.setResponse("Success, an email has been sent to " + user.getEmail() + "for confirmation.");
            } catch (Exception e) {
                msg.setResponse("User Creation failed");
            }
        }
        return (msg);
    }
}
