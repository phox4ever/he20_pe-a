package ch.hfict.blog;

import ch.hfict.blog.model.Comment;
import ch.hfict.blog.model.Post;
import ch.hfict.blog.model.User;
import ch.hfict.blog.repository.CommentRepository;
import ch.hfict.blog.repository.PostRepository;
import ch.hfict.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DefaultData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    private void init() {
        User userHomer = new User("homer", "1234");
        userRepository.save(userHomer);

        User userMarge = new User("marge", "1234");
        userRepository.save(userMarge);

        Post post1 = new Post("First Post", "First Post Content", userHomer);
        postRepository.save(post1);

        Comment comment1 = new Comment("Comment 1", post1, userHomer);
        commentRepository.save(comment1);

        Comment comment2 = new Comment("Comment 2", post1, userHomer);
        commentRepository.save(comment2);

        Comment comment3 = new Comment("Comment 3", post1, userHomer);
        commentRepository.save(comment3);
    }
}