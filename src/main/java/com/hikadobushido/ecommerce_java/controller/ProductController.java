package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.model.ProductRequest;
import com.hikadobushido.ecommerce_java.model.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable(value = "id") Long productId) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name("nama produk" + productId)
                        .price(BigDecimal.ONE)
                        .description("deskripsi product")
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(
                List.of(
                        ProductResponse.builder()
                                .name("Product 1")
                                .price(BigDecimal.ONE)
                                .description("deskripsi product")
                                .build(),
                                 ProductResponse.builder()
                                        .name("Product 1")
                                        .price(BigDecimal.ONE)
                                        .description("deskripsi product")
                                        .build()
                )
        );
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody @Valid ProductRequest request,
            @PathVariable(name = "id") Long productId
    ) {
        return ResponseEntity.ok(
                ProductResponse.builder()
                        .name(request.getName() + " " + productId)
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .build()
        );
    }
}
