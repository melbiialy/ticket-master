package com.ticketmaster.api_gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    private CircuitBreakerConfig defaultCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(3)
                .build();
    }

    private TimeLimiterConfig defaultTimeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(3))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> gatewayCircuitBreakerCustomizer() {
        CircuitBreakerConfig cbConfig = defaultCircuitBreakerConfig();
        TimeLimiterConfig tlConfig = defaultTimeLimiterConfig();

        return factory -> {
            factory.configure(builder -> builder
                    .circuitBreakerConfig(cbConfig)
                    .timeLimiterConfig(tlConfig), "searchCB");

            factory.configure(builder -> builder
                    .circuitBreakerConfig(cbConfig)
                    .timeLimiterConfig(tlConfig), "eventCB");

            factory.configure(builder -> builder
                    .circuitBreakerConfig(cbConfig)
                    .timeLimiterConfig(tlConfig), "bookingCB");
        };
    }
}