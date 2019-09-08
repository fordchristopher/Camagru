package camagru;

public class Post {
    private int id;
    private int userId;
    private String photo;
    private String date;
    private String password;

    public String getPassword() { return (this.password); }

    public void setPassword(String password) { this.password = password; }

    public int getId() {
        return (this.id);
    }

    public int getUserId() {
        return (this.userId);
    }

    public String getPhoto() {
        return (this.photo);
    }

    public String getDate() {
        return (this.date);
    }

    public void setId(int id) { this.id = id; }

    public void setUserId(int id) { this.userId = id; }

    public void setPhoto (String photo) { this.photo = photo; }

    public void setDate(String date) { this.date = date; }
}