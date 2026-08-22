package com.ticketmaster.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/search")
    public ResponseEntity<?> searchFallback() {
        return fallbackResponse("Search service");
    }

    @RequestMapping("/fallback/event")
    public ResponseEntity<?> eventFallback() {
        return fallbackResponse("Event service");
    }

    @RequestMapping("/fallback/bookings")
    public ResponseEntity<?> bookingFallback() {
        return fallbackResponse("Booking service");
    }

    private ResponseEntity<?> fallbackResponse(String serviceName) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "message", serviceName + " is temporarily unavailable. Please try again shortly.",
                        "status", "fallback"
                ));
    }
}