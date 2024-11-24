package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.ProductCategory;
import com.hikadobushido.ecommerce_java.entity.ProductCategory.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {

}
