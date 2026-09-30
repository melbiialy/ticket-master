package com.ticketmaster.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserHeaderGatewayFilterFactory
        extends AbstractGatewayFilterFactory<Object> {

    private static final String USER_ID = "X-User-Id";
    private static final String USERNAME = "X-Username";
    private static final String ROLES = "X-User-Roles";

    public UserHeaderGatewayFilterFactory() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) ->
                ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .filter(JwtAuthenticationToken.class::isInstance)
                        .map(JwtAuthenticationToken.class::cast)
                        .map(auth -> addHeaders(exchange, auth))
                        .switchIfEmpty(Mono.fromSupplier(() -> addHeaders(exchange, null)))
                        .flatMap(chain::filter),
                0);
    }

    private ServerWebExchange addHeaders(ServerWebExchange exchange, JwtAuthenticationToken auth) {
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate()
                .headers(h -> {
                    h.remove(USER_ID);
                    h.remove(USERNAME);
                    h.remove(ROLES);
                });

        if (auth != null) {
            builder.header(USER_ID, auth.getName());
            builder.header(USERNAME, Objects.requireNonNull(auth.getToken().getClaimAsString("preferred_username")));
            builder.header(ROLES, auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(",")));
        }

        return exchange.mutate().request(builder.build()).build();
    }
}