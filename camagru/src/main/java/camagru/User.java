package camagru;

import java.sql.PreparedStatement;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private byte active;
    private byte receive_notifications;
    private PreparedStatement statement;

    /*public User(String email, String username, String password, byte active, byte notify) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.receive_notifications = notify;
    }*/

    public int getId() {
        return (this.id);
    }

    public String getEmail() { return (this.email); }

    public String getUsername() {
        return (this.username);
    }

    public String getPassword() {
        return (this.password);
    }

    public byte getActive() {
        return (this.active);
    }

    public byte getReceiveNotifications() {
        return (this.receive_notifications);
    }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setActive(byte active) { this.active = active; }

    public void setReceiveNotifications(byte receive) { this.receive_notifications = receive; }


}