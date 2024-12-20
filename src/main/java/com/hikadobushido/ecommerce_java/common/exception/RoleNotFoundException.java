package com.hikadobushido.ecommerce_java.common.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
