package ch.hfict.blog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class UserController {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (Objects.equals(user.getUsername(), "test") && Objects.equals(user.getPassword(), "1234")) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
        }
    }
}
