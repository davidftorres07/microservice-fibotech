package com.fibotech.microservice.orders.controller;

import com.fibotech.microservice.orders.dto.OrderDTO;
import com.fibotech.microservice.orders.dto.OrderMapper;
import com.fibotech.microservice.orders.model.Order;
import com.fibotech.microservice.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders Controller", description = "This Controller contains the endpoint for the CRUD operations over the store orders.")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    @Operation(summary = "Add a new Order", description = "This endpoint returns a new created order.")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order createdOrder = orderService.createOrder(order);
        OrderDTO createdOrderDTO = orderMapper.toDTO(createdOrder);

        return new ResponseEntity<>(createdOrderDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get a single Order", description = "This endpoint returns a single Order by it's ID.")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            OrderDTO orderDTO = orderMapper.toDTO(order);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @Operation(summary = "Get all the registered Orders", description = "This endpoint returns a complete list of orders register.")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orderMapper.toDTOs(orders);
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update an existing Order", description = "This endpoint returns and update order by it's ID.")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable UUID orderId, @RequestBody OrderDTO orderDTO) {
        Order orderToUpdate = orderMapper.toEntity(orderDTO);
        Order updatedOrder = orderService.updateOrder(orderId, orderToUpdate);
        OrderDTO updatedOrderDTO = orderMapper.toDTO(updatedOrder);
        return new ResponseEntity<>(updatedOrderDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Cancel an Order", description = "This cancels an order using it's ID.")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
