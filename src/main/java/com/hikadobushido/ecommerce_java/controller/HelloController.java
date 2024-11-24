package com.hikadobushido.ecommerce_java.controller;

import com.hikadobushido.ecommerce_java.common.errors.BadRequestException;
import com.hikadobushido.ecommerce_java.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/bad-request")
    public ResponseEntity<String> BadRequestError() {
        throw new BadRequestException("ada error bad request");
    }

    @GetMapping("/generic-error")
    public ResponseEntity<String> genericError() {
        throw new RuntimeException("generic error");
    }

    @GetMapping("/not-found")
    public ResponseEntity<String> notFoundError() {
        throw new ResourceNotFoundException("data tidak ditemukan");
    }

}
