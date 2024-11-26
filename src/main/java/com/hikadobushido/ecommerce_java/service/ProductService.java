package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.model.PaginatedProductResponse;
import com.hikadobushido.ecommerce_java.model.ProductRequest;
import com.hikadobushido.ecommerce_java.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    Page<ProductResponse> findByPage(Pageable pageable);

    Page<ProductResponse> findByNameAndPageable(String name, Pageable pageable);

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productrequest);

    void delete(Long id);

    PaginatedProductResponse convertProductPage(Page<ProductResponse> productPage);

}
