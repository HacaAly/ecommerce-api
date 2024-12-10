package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByUserId(Long userId);

    Optional<Cart> findByUserId(Long userId);

}
