package com.fibotech.gateway.controller.request;

import lombok.Data;

@Data
public class RequestLogin {
    private final String email;
    private final String password;
}
