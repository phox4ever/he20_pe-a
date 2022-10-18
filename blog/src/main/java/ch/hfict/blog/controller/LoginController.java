package ch.hfict.blog.controller;

import ch.hfict.blog.model.JwtDto;
import ch.hfict.blog.repository.UserRepository;
import ch.hfict.blog.model.LoginDto;
import ch.hfict.blog.model.User;
import ch.hfict.blog.utils.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginDto loginDto) {
        List<User> users = userRepository.findByUsername(loginDto.getUsername());
        String pwHash = Crypto.hash(loginDto.getPassword());
        if (!users.isEmpty() && Objects.equals(users.get(0).getPassword(), pwHash)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new JwtDto(Crypto.createToken(users.get(0).getId()),users.get(0)));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JwtDto(null,null));
        }
    }
}
