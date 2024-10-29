package com.example.Ecommerce.ExceptionHandling.CustomException;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
