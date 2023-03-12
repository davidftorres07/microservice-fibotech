package com.fibotech.microservice.orders.dto;

import com.fibotech.microservice.orders.model.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-11T17:35:52-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setProductName( orderDTO.getProductName() );
        order.setQuantity( orderDTO.getQuantity() );
        order.setPrice( orderDTO.getPrice() );
        order.setCustomerName( orderDTO.getCustomerName() );
        order.setCustomerEmail( orderDTO.getCustomerEmail() );
        order.setShippingAddress( orderDTO.getShippingAddress() );
        order.setPaymentMethod( orderDTO.getPaymentMethod() );
        order.setPaymentDetails( orderDTO.getPaymentDetails() );
        order.setOrderDate( orderDTO.getOrderDate() );
        order.setShippingMethod( orderDTO.getShippingMethod() );
        order.setShippingCost( orderDTO.getShippingCost() );
        order.setTax( orderDTO.getTax() );

        return order;
    }

    @Override
    public OrderDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId( order.getId() );
        orderDTO.setProductName( order.getProductName() );
        orderDTO.setQuantity( order.getQuantity() );
        orderDTO.setPrice( order.getPrice() );
        orderDTO.setCustomerName( order.getCustomerName() );
        orderDTO.setCustomerEmail( order.getCustomerEmail() );
        orderDTO.setShippingAddress( order.getShippingAddress() );
        orderDTO.setPaymentMethod( order.getPaymentMethod() );
        orderDTO.setPaymentDetails( order.getPaymentDetails() );
        orderDTO.setOrderDate( order.getOrderDate() );
        orderDTO.setShippingMethod( order.getShippingMethod() );
        orderDTO.setShippingCost( order.getShippingCost() );
        orderDTO.setTax( order.getTax() );

        return orderDTO;
    }

    @Override
    public List<OrderDTO> toDTOs(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( orders.size() );
        for ( Order order : orders ) {
            list.add( toDTO( order ) );
        }

        return list;
    }
}
