package com.fibotech.gateway.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class JwtAuthenticationConverterTest {
    @Mock
    private JwtValidationService jwtValidationService;

    @InjectMocks
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvert_validToken() {
        String authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String username = "david";

        MockServerHttpRequest request = MockServerHttpRequest
                .get("/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .build();

        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        when(jwtValidationService.validateToken(authToken)).thenReturn(true);
        when(jwtValidationService.extractUserName(authToken)).thenReturn(username);

        Mono<Authentication> authenticationMono = jwtAuthenticationConverter.convert(exchange);
        
        assertTrue(authenticationMono.blockOptional().isPresent());
        Authentication authentication = authenticationMono.block();
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
        assertEquals(username, authentication.getPrincipal());
        assertEquals(authToken, authentication.getCredentials());
    }

    @Test
    public void testConvertInvalidToken() {
        String authToken = "invalid-token";

        MockServerHttpRequest request = MockServerHttpRequest
                .get("/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .build();

        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        when(jwtValidationService.validateToken(authToken)).thenReturn(false);

        Mono<Authentication> authenticationMono = jwtAuthenticationConverter.convert(exchange);

        assertTrue(authenticationMono.blockOptional().isEmpty());
    }

    @Test
    public void testConvertNoToken() {
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/")
                .build();

        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        Mono<Authentication> authenticationMono = jwtAuthenticationConverter.convert(exchange);

        assertTrue(authenticationMono.blockOptional().isEmpty());
    }
}