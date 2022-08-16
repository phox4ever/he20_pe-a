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
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwort darf nicht leer sein");
        }
        else if (!userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Benutzer existiert bereits");
        }
        else {
            User user = new User(userDto.getUsername(), userDto.getPassword());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user.getId().toString());
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
