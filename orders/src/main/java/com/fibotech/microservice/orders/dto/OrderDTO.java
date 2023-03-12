package com.fibotech.microservice.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fibotech.microservice.orders.model.PaymentMethod;
import com.fibotech.microservice.orders.model.ShippingMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class OrderDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID orderId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private PaymentMethod paymentMethod;
    private String paymentDetails;
    private Date orderDate;
    private ShippingMethod shippingMethod;
    private BigDecimal shippingCost;
    private BigDecimal tax;
}