package camagru;

public class Post {
    private int id;
    private int user_id;
    private String photo;
    private String description;
    private String title;
    private String date;

    Post(int user_id, String photo, String description, String title, String date) {
        this.user_id = user_id;
        this.photo = photo;
        this.description = description;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return (this.id);
    }

    public int getUserId() {
        return (this.user_id);
    }

    public String getPhoto() {
        return (this.photo);
    }

    public String getDescription() {
        return (this.description);
    }

    public String getTitle() {
        return (this.title);
    }

    public String getDate() {
        return (this.date);
    }
}