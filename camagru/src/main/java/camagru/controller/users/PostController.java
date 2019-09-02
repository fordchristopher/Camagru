package camagru.controller.users;

import camagru.Comment;
import camagru.Message;
import camagru.Post;
import camagru.User;
import camagru.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/posts", produces="application/json")
@CrossOrigin(origins="*")
public class PostController {
    private Message msg;
    private Post post;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/getAll")
    public List<Map<String, Object>> getAll() {
        return(postRepository.getAllPosts());
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public Message add(@RequestBody Post post) {
        post.setId(postRepository.getNextId("posts"));
        return (postRepository.addPost(post));
    }

    @GetMapping("/getComments")
    public List<Map<String, Object>> getComments(@RequestParam int postId) {
        return (postRepository.getComments(postId));
    }

    @GetMapping("/getAllByUserId")
    public List<Map<String, Object>> getAllByUserId(@RequestParam int userId, @RequestParam int page) {
        return (postRepository.getAllByUserId(userId, page));
    }

    @PostMapping(path = "/addComment", consumes = "application/json", produces = "application/json")
    public Message addComment (@RequestBody Comment comment) {
        return (postRepository.addComment(comment.getUserId(), comment.getPostId(), comment.getContent()));
    }

    @PostMapping(path = "/deletePost", consumes = "application/json", produces = "application/json")
    public void deletePost (@RequestBody Map<String, String> data) {
        User user = new User();
        user.setEmail(data.get("email"));
        user.setId(Integer.parseInt(data.get("userId")));
        user.setPassword(data.get("password"));
        user.setSalt(data.get("salt"));
        postRepository.deletePost(user, Integer.parseInt(data.get("postId")));
    }

    @PostMapping(path = "/likePost", consumes = "application/json", produces = "application/json")
    public Message likePost (@RequestBody Comment comment) {
        return (postRepository.likePost(comment.getUserId(), comment.getPostId()));
    }
}
