package ch.hfict.blog.controller;

import ch.hfict.blog.model.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @GetMapping("/status")
    public Status status(@RequestParam(value = "status", defaultValue = "OK") String status) {
        return new Status(status);
    }
}
