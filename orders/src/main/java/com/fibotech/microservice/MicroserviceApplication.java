package com.fibotech.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceApplication {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1500);
        SpringApplication.run(MicroserviceApplication.class, args);
    }
}
