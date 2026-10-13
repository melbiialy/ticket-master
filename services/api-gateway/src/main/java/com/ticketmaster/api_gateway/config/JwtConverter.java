package com.ticketmaster.api_gateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(extractRoles(jwt.getClaim("realm_access")));
        String userId = jwt.getSubject();
        return new JwtAuthenticationToken(jwt, grantedAuthorities, userId);
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractRoles(Map<String, Object> access) {
        if (access == null || !(access.get("roles") instanceof Collection<?> roleList)) {
            return List.of();
        }
        return roleList.stream()
                .<GrantedAuthority>map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .toList();
    }
}
