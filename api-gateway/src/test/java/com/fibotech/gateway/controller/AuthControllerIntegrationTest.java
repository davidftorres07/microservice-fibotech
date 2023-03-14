package com.fibotech.gateway.controller;


import com.fibotech.gateway.controller.request.RequestAuth;
import com.fibotech.gateway.controller.response.ResponseAuth;
import com.fibotech.gateway.security.JwtValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MapReactiveUserDetailsService userDetailsService;

    @MockBean
    private JwtValidationService jwtValidationService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLoginSuccess() {
        RequestAuth request = new RequestAuth("admin", "admin");

        UserDetails user = User.builder().password("admin").username("admin").roles("USER").build();

        when(userDetailsService.findByUsername(anyString())).thenReturn(Mono.just(user));

        when(passwordEncoder.matches(request.getPassword(), "admin")).thenReturn(true);

        when(jwtValidationService.generate(user)).thenReturn("test_token");

        webTestClient.post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseAuth.class)
                .isEqualTo(new ResponseAuth("test_token", "Authentication successful"));
    }

    @Test
    public void testLoginInvalidCredentials() {
        RequestAuth request = new RequestAuth("admin", "wrongpassword");

        UserDetails user = User.builder().password("admin").username("password").roles("USER").build();
        when(userDetailsService.findByUsername(anyString())).thenReturn(Mono.just(user));

        when(passwordEncoder.matches(request.getPassword(), "wrongpassword")).thenReturn(false);

        webTestClient.post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(ResponseAuth.class)
                .isEqualTo(new ResponseAuth("", "Authentication failed, Invalid Credentials"));
    }

    @Test
    public void testLoginUserNotFound() {
        RequestAuth request = new RequestAuth("nonuser@example.com", "password");

        when(userDetailsService.findByUsername(anyString())).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(ResponseAuth.class)
                .isEqualTo(new ResponseAuth("", "Authentication failed, User not found"));
    }
}