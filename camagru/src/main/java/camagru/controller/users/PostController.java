package camagru.controller.users;

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
        System.out.println("The id is:");
        System.out.println(post.getId());
        return (postRepository.addPost(post));
    }
}
