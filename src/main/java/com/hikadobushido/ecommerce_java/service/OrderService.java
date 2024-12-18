package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.entity.Order;
import com.hikadobushido.ecommerce_java.model.CheckoutRequest;
import com.hikadobushido.ecommerce_java.model.OrderItemResponse;
import com.hikadobushido.ecommerce_java.model.OrderResponse;
import com.hikadobushido.ecommerce_java.model.OrderStatus;
import com.hikadobushido.ecommerce_java.model.PaginatedOrderResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest checkoutRequest);

    Optional<Order> findOrderById(Long orderId);

    List<Order> findOrdersByUserId(Long userId);

    Page<OrderResponse> findOrdersByUserIdAndPageable(Long userId, Pageable pageable);

    List<Order> findOrdersByStatus(OrderStatus status);

    void cancelOrder(Long orderId);

    List<OrderItemResponse> findByOrderItemsByOrderId(Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus newStatus);

    Double calculateOrderTotal(Long orderId);

    PaginatedOrderResponse convertOrderPage(Page<OrderResponse> orderResponses);
}
