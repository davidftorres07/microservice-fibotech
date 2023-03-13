package com.fibotech.gateway.security;

import com.fibotech.gateway.exceptions.JwtAuthenticationException;
import com.fibotech.gateway.exceptions.JwtExpiredTokenException;
import com.fibotech.gateway.exceptions.JwtInvalidUserException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtValidationService validationService;
    private final MapReactiveUserDetailsService userDetailService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username = validationService.extractUserName(authToken);

        return userDetailService.findByUsername(username).flatMap(userDetails -> {
            try{
                if(!validationService.isTokenExpired(authToken)) {
                    if (validationService.isTokenValid(authToken, userDetails))
                        return Mono.just(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()));
                    else
                        throw new JwtInvalidUserException("Invalid user in token.");
                } else
                    throw new JwtExpiredTokenException("Token has expired.");
            }  catch (ExpiredJwtException e) {
                throw new JwtExpiredTokenException("Token has expired.");
            } catch (Exception e) {
                throw new JwtAuthenticationException("Failed to authenticate token.", e);
            }
        });
    }
}

