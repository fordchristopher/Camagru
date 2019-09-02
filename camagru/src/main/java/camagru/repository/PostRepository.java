package camagru.repository;

import camagru.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PostRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Post post;

    public List<Map<String, Object>> getAllPosts() {
        List<Map<String, Object>> res;
        res = jdbcTemplate.queryForList("SELECT posts.id, users.id AS userId, posts.timestamp, users.username, IFNULL(c_count.commentCount, 0) AS commentCount, IFNULL(l_count.likesCount, 0) AS likesCount FROM posts INNER JOIN users ON posts.userId = users.id LEFT JOIN (SELECT postId, COUNT(postId) AS commentCount FROM comments GROUP BY postId) AS c_count ON c_count.postId = posts.id LEFT JOIN (SELECT postId, COUNT(postId) AS likesCount FROM likes GROUP BY postId) AS l_count ON l_count.postId = posts.id WHERE users.active = 1 ORDER BY posts.timestamp DESC;");
        for (Map<String, Object> result : res) {
            result.put("url", imageToBytes("userPhotos/" + result.get("id") + ".png"));
        }
        return (res);
    }

    public String getEmailByPostId (int postId) {
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT email FROM users WHERE id = (SELECT userId FROM posts WHERE id = ?);", postId);
        if (res.size() > 0) {
            return (String.valueOf(res.get(0).get("email")));
        }
        return ("");
    }

    public String getUserNameById (int userId) {
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT username FROM users WHERE id = ?;", userId);
        if (res.size() > 0) {
            return (String.valueOf(res.get(0).get("username")));
        }
        return ("");
    }

    public Message addComment(int userId, int postId, String content) {
        Message message = new Message();
        EmailContent emailContent = new EmailContent();
        MailUtil util = new MailUtil();
        try {
            jdbcTemplate.update("INSERT INTO `comments` (`userId`, `postId`, `content`) VALUES (?, ?, ?);", userId, postId, content);
            message.setResponse("Comment added!");
            emailContent.setRecipient(getEmailByPostId(postId));
            emailContent.setSubject("Someone commented on your photo!");
            emailContent.setBody(getUserNameById(userId) + " says \"" + content + "\"");
            util.sendMail(emailContent);
        } catch (Exception e) {
            message.setResponse("Error adding comment");
            e.printStackTrace();
        }
        return (message);
    }

    public boolean authenticateUser(User user) {
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT * FROM users WHERE id = ? AND email = ? AND `password` = ?;", user.getId(), user.getEmail(), user.getPassword());
        return (res.size() > 0);
    }

    public void deletePost(User user, int postId) {
        if (authenticateUser(user)) {
            jdbcTemplate.update("DELETE FROM posts WHERE id = ?;", postId);
        }
    }

    public List<Map<String, Object>> getAllByUserId(int userId, int page) {
        List<Map<String, Object>> res;

        res = jdbcTemplate.queryForList("SELECT * FROM posts WHERE userId = ? ORDER BY `timestamp` DESC LIMIT ?, 5;", userId, page * 5 - 5);
        for (Map<String, Object> result : res) {
            result.put("url", imageToBytes("userPhotos/" + result.get("id") + ".png"));
        }
        return (res);
    }

    public Message likePost(int userId, int postId) {
        Message message = new Message();
        List<Map<String, Object>> res;
        EmailContent content = new EmailContent();
        MailUtil util = new MailUtil();

        res = jdbcTemplate.queryForList("SELECT * FROM likes WHERE userId = ? AND postId = ?;", userId, postId);
        if (res.size() > 0)
        {
            message.setResponse("Already liked this post!");
            return (message);
        }
        try {
            jdbcTemplate.update("INSERT INTO `likes` (`userId`, `postId`) VALUES (?, ?);", userId, postId);
            message.setResponse("Post liked!");
            content.setRecipient(getEmailByPostId(postId));
            content.setSubject("Someone liked your photo!");
            content.setBody(getUserNameById(userId) + " has liked your post!");
            util.sendMail(content);
        } catch (Exception e) {
            message.setResponse("Error liking post");
            e.printStackTrace();
        }
        return (message);
    }

    public byte[] convertToImg(String base64URL) throws IOException {
       return (Base64.getDecoder().decode(base64URL.getBytes("UTF-8")));
    }

    public List<Map<String, Object>> getComments(int postId) {
        return (jdbcTemplate.queryForList("SELECT comments.id, comments.userId, comments.postId, comments.content, comments.date, users.username FROM comments INNER JOIN users ON comments.userId = users.id WHERE comments.postId = ? ORDER BY comments.`date` DESC;", postId));
    }

    public void writeBytesToImg(byte[] array, String filename) throws IOException {
        File imageFile = new File(filename);

        BufferedImage imageData = ImageIO.read(new ByteArrayInputStream(array));
        ImageIO.write(imageData, "png", imageFile);
    }

    public String imageToBytes(String imageName) {
        String base64 = "";
        try {
            InputStream iSteamReader = new FileInputStream(imageName);
            byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
            base64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + base64;
    }
    public int getNextId(String table) {
        List<Map <String, Object>> res;
        BigInteger id;

        res = jdbcTemplate.queryForList("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = ? and table_schema = database();", table);
        id = (BigInteger) res.get(0).get("AUTO_INCREMENT");
        return(id.intValue());
    }

    public Message addPost(Post post) {
        String base64URL = post.getPhoto();
        Message response = new Message();
        try {
            byte[] array = convertToImg(base64URL);
            writeBytesToImg(array, "userPhotos/" + Integer.toString(post.getId()) + ".png");
            jdbcTemplate.update("INSERT INTO posts (id, userId) VALUES (?, ?);", post.getId(), post.getUserId());
            response.setResponse("Photo Successfully added");
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponse("Failed");
        }
        return (response);
    }
}
