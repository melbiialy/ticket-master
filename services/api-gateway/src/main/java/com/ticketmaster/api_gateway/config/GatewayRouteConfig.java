package com.ticketmaster.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("search-service-route", r -> r
                        .path("/search/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(c -> c
                                        .setName("searchCB")
                                        .setFallbackUri("forward:/fallback/search")))
                        .uri("lb://search-service"))

                .route("event-service-route", r -> r
                        .path("/event/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(c -> c
                                        .setName("eventCB")
                                        .setFallbackUri("forward:/fallback/event")))
                        .uri("lb://event-service"))

                .route("booking-service-route", r -> r
                        .path("/bookings/**")
                        .filters(f -> f.stripPrefix(1)
                                .circuitBreaker(c -> c
                                        .setName("bookingCB")
                                        .setFallbackUri("forward:/fallback/bookings")))
                        .uri("lb://booking-service"))
                .build();
    }
}