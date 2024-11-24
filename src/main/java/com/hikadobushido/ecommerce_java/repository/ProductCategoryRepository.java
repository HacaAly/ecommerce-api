package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.ProductCategory;
import com.hikadobushido.ecommerce_java.entity.ProductCategory.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {
    @Query(value = """
      SELECT * FROM product_category
      WHERE product_id = :productId
      """, nativeQuery = true)
    List<ProductCategory> findCategoriesByProductId(@Param("productId") Long productId);
}
