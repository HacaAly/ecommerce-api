package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
