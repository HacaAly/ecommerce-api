package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.model.PaginatedProductResponse;
import com.hikadobushido.ecommerce_java.model.ProductRequest;
import com.hikadobushido.ecommerce_java.model.ProductResponse;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
@SecurityRequirement(name = "Bearer")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable(value = "id") Long productId) {
        ProductResponse productResponse = productService.findById(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("")
    public ResponseEntity<PaginatedProductResponse> getAllProducts(
    @RequestParam(defaultValue = "0")int page,
    @RequestParam(defaultValue = "10")int size,
    @RequestParam(defaultValue = "product_id, asc") String[] sort,
    @RequestParam(required = false) String name
    ) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),(_sort[0])));
            }
        }else {
            orders.add(new Sort.Order(getSortDirection(sort[1]),(sort[0])));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<ProductResponse> productResponses;

        if (name != null && !name.isEmpty()) {
            productResponses = productService.findByNameAndPageable(name, pageable);
        } else {
            productResponses = productService.findByPage(pageable);
        }
        return ResponseEntity.ok(productService.convertProductPage(productResponses));
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        request.setUser(userInfo.getUser());
        ProductResponse response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody @Valid ProductRequest request,
            @PathVariable(name = "id") Long productId
    ) {
        ProductResponse response = productService.update(productId,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct (
            @PathVariable(name = "id") Long productId
            ) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Direction.ASC;
        } else if (direction.equals("desc")) {
            return Direction.DESC;
        }

        return Direction.ASC;
    }
}
