package com.hikadobushido.ecommerce_java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hikadobushido.ecommerce_java.entity.Role;
import com.hikadobushido.ecommerce_java.entity.User;
import com.hikadobushido.ecommerce_java.model.CheckoutRequest;
import com.hikadobushido.ecommerce_java.model.OrderResponse;
import com.hikadobushido.ecommerce_java.model.OrderStatus;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderResponse orderResponse;
    private CheckoutRequest checkoutRequest;
    private UserInfo userInfo;
    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    @Qualifier("defaultServletHandlerMapping")
    @Autowired
    private HandlerMapping defaultServletHandlerMapping;


    @BeforeEach
    void setUp() {
        checkoutRequest = CheckoutRequest.builder()
                .selectedCartItemIds(Arrays.asList(1L, 2L))
                .userAddressId(1L)
                .build();

        orderResponse = OrderResponse.builder()
                .orderId(1L)
                .status(OrderStatus.PENDING)
                .totalAmount(new BigDecimal("100.00"))
                .shippingFee(new BigDecimal("10.00"))
                .paymentUrl("http://payment.url")
                .build();


        User user = User.builder()
                .userId(1L)
                .username("Hca")
                .email("hca@example.com")
                .password("hca123")
                .enabled(true)
                .build();

        Role role = Role.builder()
                .name("ROLE_USER")
                .build();

        Role role1 = new Role();
        role1.setName("ROLE_USER");


        userInfo = UserInfo.builder()
                .user(user)
                .roles(List.of(role))
                .build();

        // Mock Security Context Holder
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testCheckout_WhenRequestIsValid() throws Exception{
        when(orderService.checkout(any(CheckoutRequest.class))).thenReturn(orderResponse);

        mockMvc.perform(post("/orders/checkout")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(checkoutRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.total_amount").value(100.00))
                .andExpect(jsonPath("$.shipping_fee").value(10.00))
                .andExpect(jsonPath("$.payment_url").value("http://payment.url"))
                .andExpect(jsonPath("$.order_id").value(1));

        verify(orderService).checkout(argThat(request -> request.getUserId().equals(1L) &&
                request.getSelectedCartItemIds().equals(checkoutRequest.getSelectedCartItemIds())
                && request.getUserAddressId().equals(checkoutRequest.getUserAddressId())));
    }

    @Test
    void testCheckout_WhenRequestInvalid() throws Exception{
        CheckoutRequest invalidRequest = new CheckoutRequest();

        mockMvc.perform(post("/orders/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }
}