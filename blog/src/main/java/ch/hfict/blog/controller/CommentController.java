package ch.hfict.blog.controller;


import ch.hfict.blog.model.*;
import ch.hfict.blog.repository.CommentRepository;
import ch.hfict.blog.repository.PostRepository;
import ch.hfict.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<String> create(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Optional<User> userOptional = userRepository.findById(commentDto.getUserId());
        Optional<Post> postOptional = postRepository.findById(id);
        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();
            Comment comment = new Comment(commentDto.getComment(), post, user);
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @GetMapping("/posts/{id}/comments")
    public List<Comment> list(@PathVariable Long id) {
        return commentRepository.findByPostId(id);
    }

    @GetMapping("/comments/{id}")
    public Comment get( @PathVariable Long id) {
        return commentRepository.findById(id).get();
    }
}
