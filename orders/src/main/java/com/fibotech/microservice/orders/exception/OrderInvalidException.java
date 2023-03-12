package com.fibotech.microservice.orders.exception;

public class OrderInvalidException extends RuntimeException {

    public OrderInvalidException(String message) {
        super(message);
    }

}