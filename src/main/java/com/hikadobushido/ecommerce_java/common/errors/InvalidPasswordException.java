package com.hikadobushido.ecommerce_java.common.errors;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
