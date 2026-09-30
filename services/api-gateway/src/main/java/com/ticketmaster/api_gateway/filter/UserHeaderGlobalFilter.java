package com.ticketmaster.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class UserHeaderGlobalFilter implements GlobalFilter, Ordered {

    public static final String USER_ID = "X-User-Id";
    public static final String USERNAME = "X-Username";
    public static final String ROLES = "X-User-Roles";

    private static final List<String> IDENTITY_HEADERS = List.of(USER_ID, USERNAME, ROLES);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctx -> Mono.justOrEmpty(ctx.getAuthentication()))
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(JwtAuthenticationToken.class::cast)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(auth -> chain.filter(withIdentityHeaders(exchange, auth.orElse(null))));
    }

    private ServerWebExchange withIdentityHeaders(ServerWebExchange exchange, JwtAuthenticationToken auth) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(headers -> {
                    IDENTITY_HEADERS.forEach(headers::remove);
                    if (auth != null) {
                        headers.set(USER_ID, auth.getName());
                        headers.set(USERNAME, usernameOf(auth));
                        headers.set(ROLES, auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")));
                    }
                })
                .build();
        return exchange.mutate().request(request).build();
    }

    private String usernameOf(JwtAuthenticationToken auth) {
        String preferred = auth.getToken().getClaimAsString("preferred_username");
        return (preferred == null || preferred.isBlank()) ? auth.getName() : preferred;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
