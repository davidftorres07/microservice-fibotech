package com.fibotech.microservice.orders.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fibotech.microservice.orders.dto.OrderDTO;
import com.fibotech.microservice.orders.dto.OrderMapper;
import com.fibotech.microservice.orders.model.Order;
import com.fibotech.microservice.orders.model.PaymentMethod;
import com.fibotech.microservice.orders.model.ShippingMethod;
import com.fibotech.microservice.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
class OrderControllerIntegrationTest {
    private final static String BASE_URL = "/orders";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        MockitoAnnotations.openMocks(this);
    }

    private OrderDTO buidlOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductName("Test Product 1");
        orderDTO.setQuantity(2);
        orderDTO.setPrice(new BigDecimal("19.99"));
        orderDTO.setCustomerName("Jose Perez");
        orderDTO.setCustomerEmail("david@gmail.com");
        orderDTO.setShippingAddress("123 Main St");
        orderDTO.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        orderDTO.setPaymentDetails("Visa **** 1234");
        orderDTO.setOrderDate(new Date());
        orderDTO.setShippingMethod(ShippingMethod.LOCAL_DELIVERY);
        orderDTO.setShippingCost(BigDecimal.ZERO);
        orderDTO.setTax(new BigDecimal("2.50"));

        return orderDTO;
    }

    private Order buidlOrder() {
        Order order = new Order();
        order.setProductName("Test Product 1");
        order.setQuantity(2);
        order.setPrice(new BigDecimal("19.99"));
        order.setCustomerName("Jose Perez");
        order.setCustomerEmail("david@gmail.com");
        order.setShippingAddress("123 Main St");
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setPaymentDetails("Visa **** 1234");
        order.setOrderDate(new Date());
        order.setShippingMethod(ShippingMethod.LOCAL_DELIVERY);
        order.setShippingCost(BigDecimal.ZERO);
        order.setTax(new BigDecimal("2.50"));
        return order;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    void itShouldCreateOrder() throws Exception {
        OrderDTO dto = buidlOrderDTO();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(dto)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }


    @Test
    public void itShouldGetOrderAndReturnOk() throws Exception {
        UUID id = UUID.randomUUID();
        Date date = new Date();
        OrderDTO orderDTO = buidlOrderDTO();
        Order order = buidlOrder();

        orderDTO.setOrderId(id);
        orderDTO.setOrderDate(date);
        order.setId(id);
        order.setOrderDate(date);

        when(orderService.getOrderById(id)).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{orderId}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrderDTO responseOrderDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderDTO.class);
        assertEquals(orderDTO.getProductName(), responseOrderDTO.getProductName());
        assertEquals(orderDTO.getQuantity(), responseOrderDTO.getQuantity());
        assertEquals(orderDTO.getPrice(), responseOrderDTO.getPrice());
        assertEquals(orderDTO.getCustomerName(), responseOrderDTO.getCustomerName());
        assertEquals(orderDTO.getCustomerEmail(), responseOrderDTO.getCustomerEmail());
        assertEquals(orderDTO.getShippingAddress(), responseOrderDTO.getShippingAddress());
        assertEquals(orderDTO.getPaymentMethod(), responseOrderDTO.getPaymentMethod());
        assertEquals(orderDTO.getPaymentDetails(), responseOrderDTO.getPaymentDetails());
        assertEquals(orderDTO.getOrderDate(), responseOrderDTO.getOrderDate());
        assertEquals(orderDTO.getShippingMethod(), responseOrderDTO.getShippingMethod());
        assertEquals(orderDTO.getShippingCost(), responseOrderDTO.getShippingCost());
        assertEquals(orderDTO.getTax(), responseOrderDTO.getTax());
    }


    @Test
    public void itShouldGetOrderNotFound() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(orderService.getOrderById(orderId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{orderId}", orderId))
                .andExpect(status().isNotFound());
    }

    @Test
    void itShouldGetAllOrders() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(BASE_URL))
                .andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void itShouldUpdateOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        Date date = new Date();
        OrderDTO orderDTO = buidlOrderDTO();
        Order order = buidlOrder();

        orderDTO.setOrderId(orderId);
        orderDTO.setOrderDate(date);
        order.setId(orderId);
        order.setOrderDate(date);

        when(orderService.updateOrder(orderId, orderMapper.toEntity(orderDTO))).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(orderDTO)))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        OrderDTO responseOrderDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderDTO.class);
        assertEquals(orderDTO.getProductName(), responseOrderDTO.getProductName());
        assertEquals(orderDTO.getQuantity(), responseOrderDTO.getQuantity());
        assertEquals(orderDTO.getPrice(), responseOrderDTO.getPrice());
        assertEquals(orderDTO.getCustomerName(), responseOrderDTO.getCustomerName());
        assertEquals(orderDTO.getCustomerEmail(), responseOrderDTO.getCustomerEmail());
        assertEquals(orderDTO.getShippingAddress(), responseOrderDTO.getShippingAddress());
        assertEquals(orderDTO.getPaymentMethod(), responseOrderDTO.getPaymentMethod());
        assertEquals(orderDTO.getPaymentDetails(), responseOrderDTO.getPaymentDetails());
        assertEquals(orderDTO.getOrderDate(), responseOrderDTO.getOrderDate());
        assertEquals(orderDTO.getShippingMethod(), responseOrderDTO.getShippingMethod());
        assertEquals(orderDTO.getShippingCost(), responseOrderDTO.getShippingCost());
        assertEquals(orderDTO.getTax(), responseOrderDTO.getTax());
    }

    @Test
    void itShouldCancelOrder() throws Exception {
        UUID orderId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{orderId}", orderId))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(orderService, times(1)).deleteOrder(orderId);
    }
}