package com.fibotech.microservice.orders.service;

import com.fibotech.microservice.orders.exception.OrderInvalidException;
import com.fibotech.microservice.orders.exception.OrderNotFoundException;
import com.fibotech.microservice.orders.model.Order;
import com.fibotech.microservice.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        if (order.getId() != null)
            throw new OrderInvalidException("Cannot create an order with ID: " + order.getId());
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(UUID orderId, Order order) {
        Order existingOrder = getOrderById(orderId);
        if (!existingOrder.getId().equals(order.getId()))
            throw new OrderInvalidException("Cannot update the ID of an existing order.");
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order existingOrder = getOrderById(orderId);
        orderRepository.delete(existingOrder);
    }
}