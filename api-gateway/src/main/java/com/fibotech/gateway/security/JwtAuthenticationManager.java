package com.fibotech.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtValidationService validationService;
    private final MapReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = authentication.getName();

        return userDetailsService.findByUsername(username).flatMap(userDetails -> {
            if (!validationService.isTokenExpired(authToken)) {
                if (validationService.isTokenValid(authToken, userDetails))
                    return Mono.just(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()));
            }
            return Mono.empty();
        });
    }

}