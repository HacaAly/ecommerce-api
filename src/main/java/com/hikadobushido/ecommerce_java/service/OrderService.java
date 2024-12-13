package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.entity.Order;
import com.hikadobushido.ecommerce_java.model.CheckoutRequest;
import com.hikadobushido.ecommerce_java.model.OrderItemResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order checkout(CheckoutRequest checkoutRequest);

    Optional<Order> findOrderById(Long orderId);

    List<Order> findOrdersByUserId(Long userId);

    List<Order> findOrdersByStatus(String status);

    void cancelOrder(Long orderId);

    List<OrderItemResponse> findByOrderItemsByOrderId(Long orderId);

    void updateOrderStatus(Long orderId, String newStatus);

    Double calculateOrderTotal(Long orderId);
}
