package com.hikadobushido.ecommerce_java.common.errors;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
