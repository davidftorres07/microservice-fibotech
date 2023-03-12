package com.fibotech.microservice.orders.service;

import com.fibotech.microservice.orders.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(UUID orderId);

    List<Order> getAllOrders();

    Order updateOrder(UUID orderId, Order order);

    void deleteOrder(UUID orderId);
}