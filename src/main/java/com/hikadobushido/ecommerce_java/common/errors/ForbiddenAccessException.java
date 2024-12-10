package com.hikadobushido.ecommerce_java.common.errors;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String message) {
        super(message);
    }
}