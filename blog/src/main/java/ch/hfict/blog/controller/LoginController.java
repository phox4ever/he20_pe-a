package ch.hfict.blog.controller;

import ch.hfict.blog.UsersRepository;
import ch.hfict.blog.model.LoginDto;
import ch.hfict.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired private UsersRepository usersRepository;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        List<User> users = usersRepository.findByUsername(loginDto.getUsername());
        if (!users.isEmpty() && Objects.equals(users.get(0).getPassword(), loginDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(users.get(0).toString());
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Benutzer existiert nicht");
        }
    }
}
