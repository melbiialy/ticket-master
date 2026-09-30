package com.ticketmaster.bookingservice;

import jakarta.ws.rs.HeaderParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/")
    public String index(@RequestHeader("X-User-Id") String userId) {
        return userId;
    }
}
