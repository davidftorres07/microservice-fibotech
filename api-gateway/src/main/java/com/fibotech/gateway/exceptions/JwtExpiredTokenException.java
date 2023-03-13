package com.fibotech.gateway.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {
    public JwtExpiredTokenException(String message) {
        super(message);
    }
}