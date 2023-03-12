package com.fibotech.gateway.controller;

import com.fibotech.gateway.controller.request.RequestLogin;
import com.fibotech.gateway.security.JwTValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {

   private final MapReactiveUserDetailsService userDetailsService;
   private final JwTValidationService jwTValidationService;

    @RequestMapping("/auth")
    Mono<ResponseEntity<String>> getRoot() {
        return Mono.just(ResponseEntity.ok("ALL OK!"));
    }

    @RequestMapping("/login")
    Mono<ResponseEntity<String>> getTest(@RequestBody RequestLogin request) {
        Mono<UserDetails> userDetails = userDetailsService.findByUsername(request.getEmail()).defaultIfEmpty(null);


        return Mono.just(ResponseEntity.ok("TEST OK!"));
    }
}
