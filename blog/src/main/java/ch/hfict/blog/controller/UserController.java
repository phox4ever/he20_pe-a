package ch.hfict.blog.controller;


import ch.hfict.blog.UsersRepository;
import ch.hfict.blog.model.Status;
import ch.hfict.blog.model.User;
import ch.hfict.blog.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/users")
    public ResponseEntity<String> create(@RequestBody UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword());
        if (!usersRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Benutzer existiert bereits");
        }
        else {
            usersRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user.getId().toString());
        }
    }

    @GetMapping("/users")
    public List<User> list() {
        return (List<User>) usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable String id) {
        return usersRepository.findById(Long.parseLong(id));
    }
}
