package camagru;

public class EmailContent {
    private String sender;
    private String password;
    private String recipient;
    private String subject;
    private String body;

    public EmailContent() {
        this.sender = "heculesforchris@gmail.com";
        this.password = "Chrisiscool!!!";
    }

    public void setRecipient(String rec) {
        this.recipient = rec;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String text) {
        this.body = text;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }

    public String getSender() {
        return this.sender;
    }

    public String getPassword() {
        return this.password;
    }
}
