package com.fibotech.microservice.orders.dto;

import com.fibotech.microservice.orders.model.Order;
import com.fibotech.microservice.orders.model.PaymentMethod;
import com.fibotech.microservice.orders.model.ShippingMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {
    @Mock
    private OrderMapper orderMapper;

    private OrderDTO orderDTO;
    private Order order;
    private List<Order> orders;

    @BeforeEach
    public void setUp() {
        UUID id = UUID.randomUUID();
        Date date = new Date();

        orderDTO = new OrderDTO();
        orderDTO.setOrderId(id);
        orderDTO.setProductName("Product Name");
        orderDTO.setQuantity(10);
        orderDTO.setPrice(BigDecimal.valueOf(1000));
        orderDTO.setCustomerName("Customer Name");
        orderDTO.setCustomerEmail("customer@gmail.com");
        orderDTO.setShippingAddress("Av. 101 SN");
        orderDTO.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        orderDTO.setPaymentDetails("1234-5678-9012-3456");
        orderDTO.setOrderDate(date);
        orderDTO.setShippingMethod(ShippingMethod.PICKUP);
        orderDTO.setShippingCost(BigDecimal.valueOf(5.0));
        orderDTO.setTax(BigDecimal.valueOf(10.0));

        order = new Order();
        order.setId(id);
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

        orders = new ArrayList<>();
        orders.add(order);
    }

    @Test
    public void testToEntity() {
        when(orderMapper.toEntity(orderDTO)).thenReturn(order);
        Order result = orderMapper.toEntity(orderDTO);


        assertNotNull(order);
        assertEquals(order, result);
        assertEquals(orderDTO.getOrderId(), order.getId());
        assertEquals(orderDTO.getProductName(), order.getProductName());
        assertEquals(orderDTO.getQuantity(), order.getQuantity());
        assertEquals(orderDTO.getPrice(), order.getPrice());
        assertEquals(orderDTO.getCustomerName(), order.getCustomerName());
        assertEquals(orderDTO.getCustomerEmail(), order.getCustomerEmail());
        assertEquals(orderDTO.getShippingAddress(), order.getShippingAddress());
        assertEquals(orderDTO.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(orderDTO.getPaymentDetails(), order.getPaymentDetails());
        assertEquals(orderDTO.getOrderDate(), order.getOrderDate());
        assertEquals(orderDTO.getShippingMethod(), order.getShippingMethod());
        assertEquals(orderDTO.getShippingCost(), order.getShippingCost());
        assertEquals(orderDTO.getTax(), order.getTax());
    }

    @Test
    public void testToDTO() {
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);
        OrderDTO result = orderMapper.toDTO(order);


        assertNotNull(orderDTO);
        assertEquals(orderDTO, result);
        assertEquals(order.getId(), orderDTO.getOrderId());
        assertEquals(order.getProductName(), orderDTO.getProductName());
        assertEquals(order.getQuantity(), orderDTO.getQuantity());
        assertEquals(order.getPrice(), orderDTO.getPrice());
        assertEquals(order.getCustomerName(), orderDTO.getCustomerName());
        assertEquals(order.getCustomerEmail(), orderDTO.getCustomerEmail());
        assertEquals(order.getShippingAddress(), orderDTO.getShippingAddress());
        assertEquals(order.getPaymentMethod(), orderDTO.getPaymentMethod());
        assertEquals(order.getPaymentDetails(), orderDTO.getPaymentDetails());
        assertEquals(order.getOrderDate(), orderDTO.getOrderDate());
        assertEquals(order.getShippingMethod(), orderDTO.getShippingMethod());
        assertEquals(order.getShippingCost(), orderDTO.getShippingCost());
        assertEquals(order.getTax(), orderDTO.getTax());
    }

    @Test
    public void testToDTOs() {
        when(orderMapper.toDTOs(orders)).thenReturn(List.of(orderDTO));
        List<OrderDTO> result = orderMapper.toDTOs(orders);
        
        assertEquals(List.of(orderDTO), result);
    }
}