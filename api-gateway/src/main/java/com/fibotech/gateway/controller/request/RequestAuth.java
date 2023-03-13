package com.fibotech.gateway.controller.request;

import lombok.Data;

@Data
public class RequestAuth {
    private final String email;
    private final String password;
}
