package camagru.repository;

import camagru.Message;
import camagru.Post;
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
        System.out.println(System.getProperty("user.dir"));
        for (Map<String, Object> result : res) {
            result.put("url", imageToBytes("userPhotos/" + result.get("id") + ".png"));
        }
        return (res);
    }

    public Message addComment(int postId, int userId, String content) {
        Message message = new Message();
        try {
            jdbcTemplate.update("INSERT INTO `comments` (`userId`, `postId`, `content`) VALUES (?, ?, ?);", postId, userId, content);
            message.setResponse("Comment added!");
        } catch (Exception e) {
            message.setResponse("Error adding comment");
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
