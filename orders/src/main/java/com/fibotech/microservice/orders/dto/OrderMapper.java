package com.fibotech.microservice.orders.dto;

import com.fibotech.microservice.orders.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Mapping(target = "orderId", source = "id")
    OrderDTO toDTO(Order order);

    List<OrderDTO> toDTOs(List<Order> orders);
}
