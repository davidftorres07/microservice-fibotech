package com.fibotech.gateway.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtInvalidUserException extends AuthenticationException {
    public JwtInvalidUserException(String message) {
        super(message);
    }
}