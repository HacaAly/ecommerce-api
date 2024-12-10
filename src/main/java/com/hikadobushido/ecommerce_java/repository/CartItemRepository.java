package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = """
        SELECT ci.* FROM cart_items ci 
        JOIN carts c
        ON c.cart_id = ci.cart_id
        WHERE c.user_id = :userId
        """, nativeQuery = true)
    List<CartItem> getUserCartItems(Long userId);

    @Query(value = """
        SELECT * FROM cart_items
        WHERE cart_id = :cartId
        AND product_id = :productId
        """, nativeQuery = true)
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Query(value = """
        DELETE FROM cart_items
        WHERE cart_id = :cartId
        """, nativeQuery = true)
    void deleteAllByCartId(Long cartId);
}
