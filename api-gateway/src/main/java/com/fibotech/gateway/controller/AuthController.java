package com.fibotech.gateway.controller;

import com.fibotech.gateway.controller.request.RequestAuth;
import com.fibotech.gateway.controller.response.ResponseAuth;
import com.fibotech.gateway.security.JwtValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MapReactiveUserDetailsService userDetailsService;
    private final JwtValidationService jwTValidationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    public Mono<ResponseEntity<ResponseAuth>> login(@RequestBody RequestAuth request) {
        var userDetails = userDetailsService.findByUsername(request.getEmail());

        return userDetails.flatMap(user -> {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwTValidationService.generate(user);
                return Mono.just(ResponseEntity.ok(new ResponseAuth(token, "Authentication successful")));
            }
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseAuth("", "Authentication failed, Invalid Credentials")));
        }).switchIfEmpty(
                Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseAuth("", "Authentication failed, User not found")))
        );
    }
}
