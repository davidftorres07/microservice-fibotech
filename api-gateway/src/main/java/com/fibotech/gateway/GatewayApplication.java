package com.fibotech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class GatewayApplication {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(2000);
        SpringApplication.run(GatewayApplication.class, args);
    }
}