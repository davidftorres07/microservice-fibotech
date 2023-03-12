package com.fibotech.microservice.orders.service;


import com.fibotech.microservice.orders.model.Order;
import com.fibotech.microservice.orders.model.PaymentMethod;
import com.fibotech.microservice.orders.model.ShippingMethod;
import com.fibotech.microservice.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class OrderServiceImpDataJpaTest {

    @Autowired
    private OrderRepository orderRepository;

    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImp(orderRepository);

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
        Order result = orderService.createOrder(order);

        assertNotNull(order);
        assertEquals(order, result);
        assertEquals(order.getId(), order.getId());
        assertEquals(order.getProductName(), order.getProductName());
        assertEquals(order.getQuantity(), order.getQuantity());
        assertEquals(order.getPrice(), order.getPrice());
        assertEquals(order.getCustomerName(), order.getCustomerName());
        assertEquals(order.getCustomerEmail(), order.getCustomerEmail());
        assertEquals(order.getShippingAddress(), order.getShippingAddress());
        assertEquals(order.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(order.getPaymentDetails(), order.getPaymentDetails());
        assertEquals(order.getOrderDate(), order.getOrderDate());
        assertEquals(order.getShippingMethod(), order.getShippingMethod());
        assertEquals(order.getShippingCost(), order.getShippingCost());
        assertEquals(order.getTax(), order.getTax());
    }

    @Test
    void testGetOrderById() {
        Order createdResult = orderRepository.save(order);

        Order result = orderService.getOrderById(order.getId());

        assertNotNull(result);
        assertEquals(result, result);
        assertEquals(result.getId(), createdResult.getId());
        assertEquals(result.getProductName(), createdResult.getProductName());
        assertEquals(result.getQuantity(), createdResult.getQuantity());
        assertEquals(result.getPrice(), createdResult.getPrice());
        assertEquals(result.getCustomerName(), createdResult.getCustomerName());
        assertEquals(result.getCustomerEmail(), createdResult.getCustomerEmail());
        assertEquals(result.getShippingAddress(), createdResult.getShippingAddress());
        assertEquals(result.getPaymentMethod(), createdResult.getPaymentMethod());
        assertEquals(result.getPaymentDetails(), createdResult.getPaymentDetails());
        assertEquals(result.getOrderDate(), createdResult.getOrderDate());
        assertEquals(result.getShippingMethod(), createdResult.getShippingMethod());
        assertEquals(result.getShippingCost(), createdResult.getShippingCost());
        assertEquals(result.getTax(), createdResult.getTax());
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Collections.singletonList(order);
        orders.forEach(o -> orderRepository.save(o));

        var result = orderService.getAllOrders();
        assertNotNull(result);
        assertEquals(result.size(), orders.size());
    }

    @Test
    void testUpdateOrder() {
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

        Order createdResult = orderRepository.save(order);

        var result = orderService.updateOrder(order.getId(),order);

        assertEquals(result, createdResult);
        assertEquals(result.getId(), createdResult.getId());
        assertEquals(result.getProductName(), createdResult.getProductName());
        assertEquals(result.getQuantity(), createdResult.getQuantity());
        assertEquals(result.getPrice(), createdResult.getPrice());
        assertEquals(result.getCustomerName(), createdResult.getCustomerName());
        assertEquals(result.getCustomerEmail(), createdResult.getCustomerEmail());
        assertEquals(result.getShippingAddress(), createdResult.getShippingAddress());
        assertEquals(result.getPaymentMethod(), createdResult.getPaymentMethod());
        assertEquals(result.getPaymentDetails(), createdResult.getPaymentDetails());
        assertEquals(result.getOrderDate(), createdResult.getOrderDate());
        assertEquals(result.getShippingMethod(), createdResult.getShippingMethod());
        assertEquals(result.getShippingCost(), createdResult.getShippingCost());
        assertEquals(result.getTax(), createdResult.getTax());
    }

    @Test
    void testDeleteOrder() {
        Order result = orderService.createOrder(order);
        assertDoesNotThrow(()-> orderService.deleteOrder(result.getId()));
    }

}