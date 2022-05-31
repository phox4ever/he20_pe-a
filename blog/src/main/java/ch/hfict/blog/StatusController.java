package ch.hfict.blog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class StatusController {
    @GetMapping("/status")
    public Status status(@RequestParam(value = "status", defaultValue = "OK") String status) {
        return new Status(status);
    }
}
