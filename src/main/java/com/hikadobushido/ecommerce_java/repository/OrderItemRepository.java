package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> finByOrderId(Long orderId);

    @Query(value = """
        SELECT oi.* FROM order_items oi
        JOIN orders o ON o.order_id = oi.order_id
        WHERE o.user_id = :userId
        AND oi.productId = :productId
        """, nativeQuery = true)
    List<OrderItem> findByUserAndProduct(Long userId, Long productId);

    @Query(value = """
        SELECT SUM(quantity * price) FROM order_items
        WHERE order_id = :orderId
        """, nativeQuery = true)
    Double calculateTotalOrder(Long orderId);
}
