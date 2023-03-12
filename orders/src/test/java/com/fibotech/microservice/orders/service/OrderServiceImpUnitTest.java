package com.fibotech.microservice.orders.service;


import com.fibotech.microservice.orders.exception.OrderInvalidException;
import com.fibotech.microservice.orders.model.Order;
import com.fibotech.microservice.orders.model.PaymentMethod;
import com.fibotech.microservice.orders.model.ShippingMethod;
import com.fibotech.microservice.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceImpUnitTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImp orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Date date = new Date();
        order = new Order();
        order.setProductName("Product Name");
        order.setQuantity(10);
        order.setPrice(BigDecimal.valueOf(1000));
        order.setCustomerName("Customer Name");
        order.setCustomerEmail("customer@gmail.com");
        order.setShippingAddress("Av. 101 SN");
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setPaymentDetails("1234-5678-9012-3456");
        order.setOrderDate(date);
        order.setShippingMethod(ShippingMethod.PICKUP);
        order.setShippingCost(BigDecimal.valueOf(5.0));
        order.setTax(BigDecimal.valueOf(10.0));
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(result, result);
        assertEquals(result.getId(), order.getId());
        assertEquals(result.getProductName(), order.getProductName());
        assertEquals(result.getQuantity(), order.getQuantity());
        assertEquals(result.getPrice(), order.getPrice());
        assertEquals(result.getCustomerName(), order.getCustomerName());
        assertEquals(result.getCustomerEmail(), order.getCustomerEmail());
        assertEquals(result.getShippingAddress(), order.getShippingAddress());
        assertEquals(result.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(result.getPaymentDetails(), order.getPaymentDetails());
        assertEquals(result.getOrderDate(), order.getOrderDate());
        assertEquals(result.getShippingMethod(), order.getShippingMethod());
        assertEquals(result.getShippingCost(), order.getShippingCost());
        assertEquals(result.getTax(), order.getTax());
    }

    @Test
    void testCreateOrderInvalidException() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        order.setId(UUID.randomUUID());

        assertThrows(OrderInvalidException.class, () -> orderService.createOrder(order));
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));
        order.setId(UUID.randomUUID());
        
        Order result = orderService.getOrderById(order.getId());

        assertNotNull(result);
        assertEquals(result, order);
        assertEquals(result.getId(), order.getId());
        assertEquals(result.getProductName(), order.getProductName());
        assertEquals(result.getQuantity(), order.getQuantity());
        assertEquals(result.getPrice(), order.getPrice());
        assertEquals(result.getCustomerName(), order.getCustomerName());
        assertEquals(result.getCustomerEmail(), order.getCustomerEmail());
        assertEquals(result.getShippingAddress(), order.getShippingAddress());
        assertEquals(result.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(result.getPaymentDetails(), order.getPaymentDetails());
        assertEquals(result.getOrderDate(), order.getOrderDate());
        assertEquals(result.getShippingMethod(), order.getShippingMethod());
        assertEquals(result.getShippingCost(), order.getShippingCost());
        assertEquals(result.getTax(), order.getTax());
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        assertNotNull(orderService.getAllOrders());
        assertEquals(orderService.getAllOrders().size(),1);
    }

    @Test
    void testUpdateOrderWithOrderInvalidException() {
        Order test = new Order();
        test.setId(UUID.randomUUID());
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(test));

        assertThrows(OrderInvalidException.class, () ->
                orderService.updateOrder(UUID.randomUUID(),order));
    }

    @Test
    void testUpdateOrder() {
        order.setId(UUID.randomUUID());
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        order.setProductName("New Product Name");
        order.setQuantity(80);
        order.setPrice(BigDecimal.valueOf(5000));
        order.setCustomerName("Customer New Name");
        order.setCustomerEmail("new@gmail.com");
        order.setShippingAddress("Av. 102 #370");
        order.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        order.setPaymentDetails("1234-5678-9012-****");
        order.setOrderDate(new Date());
        order.setShippingMethod(ShippingMethod.LOCAL_DELIVERY);
        order.setShippingCost(BigDecimal.valueOf(15.0));
        order.setTax(BigDecimal.valueOf(100.0));

        var result = orderService.updateOrder(order.getId(),order);

        assertEquals(result, order);
        assertEquals(result.getId(), order.getId());
        assertEquals(result.getProductName(), order.getProductName());
        assertEquals(result.getQuantity(), order.getQuantity());
        assertEquals(result.getPrice(), order.getPrice());
        assertEquals(result.getCustomerName(), order.getCustomerName());
        assertEquals(result.getCustomerEmail(), order.getCustomerEmail());
        assertEquals(result.getShippingAddress(), order.getShippingAddress());
        assertEquals(result.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(result.getPaymentDetails(), order.getPaymentDetails());
        assertEquals(result.getOrderDate(), order.getOrderDate());
        assertEquals(result.getShippingMethod(), order.getShippingMethod());
        assertEquals(result.getShippingCost(), order.getShippingCost());
        assertEquals(result.getTax(), order.getTax());
    }

    @Test
    void testDeleteOrder() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        doNothing().when(orderRepository).deleteById(any(UUID.class));

        orderService.deleteOrder(order.getId());
    }
}