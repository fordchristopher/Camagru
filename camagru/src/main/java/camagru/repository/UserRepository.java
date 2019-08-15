package camagru.repository;

import camagru.Message;
import camagru.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public Message createUser(User user) {
        Message msg = new Message();
        try {
            jdbcTemplate.update("INSERT INTO users (`email`, `username`, `password`, `active`, `receive_notifications`) VALUES (?, ?, ?, ?, ?);",
                    user.getEmail(), user.getUsername(), user.getPassword(), user.getActive(), user.getReceiveNotifications());
            msg.setResponse("Success");
        } catch (Exception e) {
            msg.setResponse("User Creation failed");
        }
        return (msg);
    }
}
