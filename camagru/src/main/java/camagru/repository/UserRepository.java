package camagru.repository;

import camagru.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.rmi.MarshalException;
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

    public String getSaltByEmail (String email) {
        List<Map<String, Object>> res;
        String salt;

        res = jdbcTemplate.queryForList("SELECT salt FROM users WHERE email = ?;", email);
        if (res.size() > 0) {
            salt = String.valueOf(res.get(0).get("salt"));
        } else {
            salt = "";
        }
        return (salt);
    }

    public HashMap<String, Object> authenticate(String email, String password) {
        HashMap<String, Object> data;
        List<Map<String, Object>> res;
        String salt;
        User user = new User();

        user.setPassword(password);
        user.setEmail(email);
        user.setSalt(getSaltByEmail(email));
        data = new HashMap<String, Object>();
        if (user.getSalt() == "")
        {
            data.put("data", "Invalid Login");
            return (data);
        }
        String securePassword = PasswordUtils.generateSecurePassword(password, user.getSalt());
        res = jdbcTemplate.queryForList("SELECT * FROM users WHERE email = ? AND password = ?;", user.getEmail(), securePassword);
        if (res.size() > 0) {
            if ((int) res.get(0).get("active") == 0) {
                data.put("data", "Please activate your account. Check your email");
            } else {
                data.put("data", res.get(0));
            }
        } else {
            data.put("data", "Invalid Login");
        }
        return (data);
    }

    public boolean emailExists(String email) {
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT * FROM users WHERE email = ?", email);
        return (res.size() > 0);
    }

    public Message updateEmail(User user) {
        Message msg = new Message();
        if (emailExists(user.getEmail()) == false) {
            try {
                jdbcTemplate.update("UPDATE users SET email = ? WHERE id = ? AND password = ?;", user.getEmail(), user.getId(), user.getPassword());
                msg.setResponse("Email address updated!");
            } catch (Exception e) {
                msg.setResponse("Email address update failed");
            }
        } else {
            msg.setResponse("Email address already exists");
        }
        return (msg);
    }

    public Message updateUsername(User user) {
        Message msg = new Message();
        try {
            jdbcTemplate.update("UPDATE users SET username = ? WHERE id = ? AND password = ?;", user.getUsername(), user.getId(), user.getPassword());
            msg.setResponse("Username updated!");
        } catch (Exception e) {
            msg.setResponse("Username update failed");
        }
        return (msg);
    }

    public Message updatePassword(User user, String oldPass) {
        Message msg = new Message();
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT * FROM users WHERE id = ? AND password = ?;", user.getId(), oldPass);
        if (res.size() > 0)
        {
            try {
                user.setSalt(PasswordUtils.getSalt(30));
                String securePassword = PasswordUtils.generateSecurePassword(user.getPassword(), user.getSalt());
                jdbcTemplate.update("UPDATE users SET password = ?, salt = ? WHERE id = ?;", securePassword, user.getSalt(), user.getId());
                msg.setResponse("Password updated!");
            } catch (Exception e) {
                msg.setResponse("Password update failed");
                e.printStackTrace();
            }
        } else {
            msg.setResponse("Authenication failure");
        }
        return (msg);
    }

    public void resetPassword(User user) {
        List<Map<String, Object>> res;
        MailUtil util = new MailUtil();
        EmailContent content = new EmailContent();
        res = jdbcTemplate.queryForList("SELECT * FROM users where email = ?;", user.getEmail());
        if (res.size() > 0) {
            String random = genRandomStr();
            user.setPassword(random);
            user.setSalt(PasswordUtils.getSalt(30));
            String securePassword = PasswordUtils.generateSecurePassword(user.getPassword(), user.getSalt());
            try {
                jdbcTemplate.update("UPDATE users SET password = ?, salt = ? WHERE email = ?;", securePassword, user.getSalt(), user.getEmail());
                content.setRecipient(user.getEmail());
                content.setSubject("Forgotten password request");
                content.setBody("Hello, a forgotten password request has been sent for this email address.  Please use the following password to sign in and you can change your password upon successful login: " + random);
                util.sendMail(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String genRandomStr() {
        int character;
        int length = 35;
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder(length);
        while (length-- > 0) {
            character = (int)(Math.random()*base.length());
            builder.append(base.charAt(character));
        }
        return builder.toString();
    }

    public int getNextId(String table) {
        List<Map <String, Object>> res;
        BigInteger id;

        res = jdbcTemplate.queryForList("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = ? and table_schema = database();", table);
        id = (BigInteger) res.get(0).get("AUTO_INCREMENT");
        return(id.intValue());
    }

    public Message confirmRegister(String key) {
        List<Map<String, Object>> res;
        Message message = new Message();
        String email;
        int id;

        res = jdbcTemplate.queryForList("SELECT userId FROM unique_urls WHERE url = ?;", key);
        if (res.size() > 0) {
            id = (int) res.get(0).get("userId");
            try {
                jdbcTemplate.update("UPDATE users SET active = 1 WHERE id = ?;", id);
                message.setResponse("User " + id + " activated");
            } catch (Exception e) {
                message.setResponse("User activation failed");
            }
            jdbcTemplate.update("DELETE FROM unique_urls WHERE url = ?;", key);
        } else {
            message.setResponse("User not found");
        }
        return (message);
    }

    public Message createUser(User user) {
        String url;
        Message msg = new Message();
        MailUtil util = new MailUtil();
        EmailContent content = new EmailContent();
        user.setSalt(PasswordUtils.getSalt(30));
        String securePassword = PasswordUtils.generateSecurePassword(user.getPassword(), user.getSalt());

        if (MailUtil.isValid(user.getEmail()) == false)
        {
            msg.setResponse("Invalid Email address");
            return (msg);
        }
        if (this.getUserId(user) > 0) {
            msg.setResponse("Duplicate email address, please enter a new email.");
            return (msg);
        }
        else {
            try {
                content.setRecipient(user.getEmail());
                content.setSubject("Camagru new account confirmation");
                user.setId(getNextId("users"));
                jdbcTemplate.update("INSERT INTO users (`email`, `username`, `password`, `salt`) VALUES (?, ?, ?, ?);",
                        user.getEmail(), user.getUsername(), securePassword, user.getSalt());
                url = genRandomStr();
                jdbcTemplate.update("INSERT INTO unique_urls (userId, url) VALUES (?, ?);", user.getId(), url);
                content.setBody("Thanks for signing up with us. Here is the link to activate your account: http://localhost:8000/users/confirm?key=" + url);
                util.sendMail(content);
                msg.setResponse("Success, an email has been sent to " + user.getEmail() + "for confirmation.");
            } catch (Exception e) {
                e.printStackTrace();
                msg.setResponse("User Creation failed");
            }
        }
        return (msg);
    }

    public int get_notification_preference(User user) {
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT receive_notifications FROM users WHERE id = ? AND password = ?;", user.getId(), user.getPassword());
        if (res.size() > 0)
            return ((int) res.get(0).get("receive_notifications"));
        return (-1);
    }

    public Message updateNotificationPreferences(User user) {
        Message message = new Message();

        int oldPreference = get_notification_preference(user);
        if (oldPreference == -1)
        {
            message.setResponse("Update failed!");
            return (message);
        }
        int newPreference = oldPreference == 1 ? 0 : 1;

        try {
            jdbcTemplate.update("UPDATE users set receive_notifications = ? WHERE id = ? AND password = ?;", newPreference, user.getId(), user.getPassword());
            message.setResponse("Update confirmed!");
        } catch (Exception e) {
            e.printStackTrace();
            message.setResponse("Update failed!");
        }
        return (message);
    }
}
