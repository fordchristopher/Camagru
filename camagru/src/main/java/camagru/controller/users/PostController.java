package camagru.controller.users;

import camagru.Comment;
import camagru.Message;
import camagru.Post;
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

    @PostMapping(path = "/addComment", consumes = "application/json", produces = "application/json")
    public Message addComment (@RequestBody Comment comment) {
        return postRepository.addComment(comment.getUserId(), comment.getPostId(), comment.getContent());
    }
}
