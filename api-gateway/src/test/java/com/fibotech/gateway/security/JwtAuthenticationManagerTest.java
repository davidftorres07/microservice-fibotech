package com.fibotech.gateway.security;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class JwtAuthenticationManagerTest {

    @Mock
    private JwtValidationService validationService;

    @Mock
    private MapReactiveUserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationManager authenticationManager;

    public JwtAuthenticationManagerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateValidTokenShouldReturnAuthenticatedUser() {
        String username = "test-user";
        String password = "password";
        String authToken = "validAuthToken";

        when(userDetailsService.findByUsername(username))
                .thenReturn(Mono.just(new User(username, password, Collections.emptyList())));

        when(validationService.isTokenExpired(authToken))
                .thenReturn(false);

        when(validationService.isTokenValid(authToken, new User(username, password, Collections.emptyList())))
                .thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, authToken);
        Authentication result = authenticationManager.authenticate(authentication).block();

        assertEquals(username, result != null ? result.getName() : null);
        assertEquals(password, result != null ? result.getCredentials() : null);
    }

    @Test
    void authenticateInvalidTokenShouldReturnEmpty() {
        String username = "test-user";
        String password = "password";
        String authToken = "invalidAuthToken";

        when(userDetailsService.findByUsername(username))
                .thenReturn(Mono.just(new User(username, password, Collections.emptyList())));

        when(validationService.isTokenExpired(authToken))
                .thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, authToken);
        Authentication result = authenticationManager.authenticate(authentication).block();

        assertNull(result);
    }

    private static class User extends org.springframework.security.core.userdetails.User {
        User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
        }
    }
}