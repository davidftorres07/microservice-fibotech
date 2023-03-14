package com.fibotech.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Value("${spring.api-gateway.security.user}")
    private String DEFAULT_USER;
    @Value("${spring.api-gateway.security.password}")
    private String DEFAULT_PASSWORD;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Default User: " + DEFAULT_USER);
            System.out.println("Default Password: " + DEFAULT_PASSWORD);
        };
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, AuthenticationWebFilter authWebFilter) {
        return http
                .csrf().disable()
                .authorizeExchange(exchanges -> {
                            exchanges.pathMatchers(HttpMethod.POST, "/login").permitAll();
                            exchanges.anyExchange().authenticated();
                        }
                )
                .httpBasic().disable()
                .formLogin().disable()
                .addFilterAt(authWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter authenticationWebFilter(JwtAuthenticationManager jwtAuthManager, JwtAuthenticationConverter jwtAuthConverter) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(jwtAuthManager);
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthConverter);
        return authenticationWebFilter;
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username(DEFAULT_USER)
                .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


