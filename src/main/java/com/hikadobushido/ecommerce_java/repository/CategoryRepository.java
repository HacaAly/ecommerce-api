package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
