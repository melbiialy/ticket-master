package com.ticketmaster.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(
                exchange ->
                    exchange.pathMatchers(Constant.BOOKING_SERVICE).authenticated()
                            .pathMatchers(Constant.EVENT_SERVICE).authenticated()
                            .pathMatchers(Constant.SEARCH_SERVICE).authenticated()
                            .pathMatchers("/error").permitAll()

        ).oauth2ResourceServer(oauth2 ->oauth2.jwt(jwtSpec ->
                jwtSpec.jwtAuthenticationConverter(reactiveJwtConverter())));
        return http.build();
    }
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> reactiveJwtConverter() {
        return new ReactiveJwtAuthenticationConverterAdapter(new JwtConverter());
    }


}
