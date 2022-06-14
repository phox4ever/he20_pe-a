package ch.hfict.blog.controller;


import ch.hfict.blog.model.Post;
import ch.hfict.blog.model.PostDto;
import ch.hfict.blog.model.User;
import ch.hfict.blog.repository.PostRepository;
import ch.hfict.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/posts")
    public ResponseEntity<String> create(@RequestBody PostDto postDto) {
        Optional<User> authorOptional = usersRepository.findById(postDto.getUserId());
        if (authorOptional.isPresent()) {
            User user = authorOptional.get();
            Post post = new Post(postDto.getTitle(), postDto.getContent(), user);
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @GetMapping("/posts")
    public List<Post> list() {
        return (List<Post>) postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post get(@PathVariable Long id) {
        return postRepository.findById(id).get();
    }
}
