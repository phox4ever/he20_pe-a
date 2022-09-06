package ch.hfict.blog.controller;


import ch.hfict.blog.repository.UserRepository;
import ch.hfict.blog.model.User;
import ch.hfict.blog.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else if (!userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        else {
            User user = new User(userDto.getUsername(), userDto.getPassword());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/users")
    public List<User> list() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<User> get(@PathVariable String id) {
        return userRepository.findById(Long.parseLong(id));
    }
}
