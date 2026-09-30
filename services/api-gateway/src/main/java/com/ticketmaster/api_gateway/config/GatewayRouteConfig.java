package com.ticketmaster.api_gateway.config;

import com.ticketmaster.api_gateway.filter.UserHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.Duration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           UserHeaderGatewayFilterFactory userHeader) {

        GatewayFilter userHeaderFilter = userHeader.apply(new Object());

        return builder.routes()
                .route("search-service-route", r -> r
                        .path(Constant.SEARCH_SERVICE)
                        .filters(f -> f.stripPrefix(1)
                                .filter(userHeaderFilter)
                                .circuitBreaker(c -> c
                                        .setName("searchCB")
                                        .setFallbackUri("forward:/fallback/search"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setSeries(HttpStatus.Series.SERVER_ERROR)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofMillis(1000), 2, false)))
                        .uri("lb://search-service"))

                .route("event-service-route", r -> r
                        .path(Constant.EVENT_SERVICE)
                        .filters(f -> f.stripPrefix(1)
                                .filter(userHeaderFilter)
                                .circuitBreaker(c -> c
                                        .setName("eventCB")
                                        .setFallbackUri("forward:/fallback/event"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setSeries(HttpStatus.Series.SERVER_ERROR)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(200), Duration.ofMillis(1000), 2, false)))
                        .uri("lb://event-service"))

                .route("booking-service-route", r -> r
                        .path(Constant.BOOKING_SERVICE)
                        .filters(f -> f.stripPrefix(1)
                                .filter(userHeaderFilter)
                                .circuitBreaker(c -> c
                                        .setName("bookingCB")
                                        .setFallbackUri("forward:/fallback/bookings"))
                                .retry(retryConfig -> retryConfig
                                        .setRetries(2)
                                        // GET only: POST/PUT are excluded to avoid duplicate bookings
                                        .setMethods(HttpMethod.GET)
                                        .setSeries(HttpStatus.Series.SERVER_ERROR)
                                        .setBackoff(Duration.ofMillis(300), Duration.ofMillis(1500), 2, false)))
                        .uri("lb://booking-service"))
                .build();
    }
}