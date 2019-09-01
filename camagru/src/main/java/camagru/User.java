package camagru;

import java.util.Map;
import org.apache.commons.codec.digest.Crypt;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private String salt;
    private int active;
    private int receive_notifications;

    public String getSalt() {
        return (this.salt);
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getId() {
        return (this.id);
    }

    public String getEmail() { return (this.email); }

    public String getUsername() {
        return (this.username);
    }

    public String getPassword() { return (password); }

    public int getActive() {
        return (this.active);
    }

    public int getReceiveNotifications() {
        return (this.receive_notifications);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(int active) { this.active = active; }

    public void setReceiveNotifications(int receive) { this.receive_notifications = receive; }

    public void setMap(Map<String, Object> res) {
        this.username = String.valueOf(res.get("username"));
        this.password = String.valueOf(res.get("password"));
        this.email = String.valueOf(res.get("email"));
        this.active = (Integer) res.get("active");
        this.receive_notifications = (Integer) res.get("receive_notifications");
    }
}