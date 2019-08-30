package camagru.repository;

import camagru.Message;
import camagru.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
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
        res = jdbcTemplate.queryForList("SELECT * FROM posts ORDER BY `timestamp` DESC;");
        return (res);
    }

    public byte[] convertToImg(String base64URL) throws IOException {
        return (Base64.getDecoder().decode(base64URL.getBytes("UTF-8")));
    }

    public void writeBytesToImg(byte[] array, String filename) throws IOException {
        File imageFile = new File(filename);

        BufferedImage imageData = ImageIO.read(new ByteArrayInputStream(array));
        ImageIO.write(imageData, "png", imageFile);
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
