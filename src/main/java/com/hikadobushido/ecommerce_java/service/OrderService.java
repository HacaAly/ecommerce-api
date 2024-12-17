package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.entity.Order;
import com.hikadobushido.ecommerce_java.model.CheckoutRequest;
import com.hikadobushido.ecommerce_java.model.OrderItemResponse;
import com.hikadobushido.ecommerce_java.model.OrderResponse;
import com.hikadobushido.ecommerce_java.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest checkoutRequest);

    Optional<Order> findOrderById(Long orderId);

    List<Order> findOrdersByUserId(Long userId);

    List<Order> findOrdersByStatus(OrderStatus status);

    void cancelOrder(Long orderId);

    List<OrderItemResponse> findByOrderItemsByOrderId(Long orderId);

    void updateOrderStatus(Long orderId, OrderStatus newStatus);

    Double calculateOrderTotal(Long orderId);
}
