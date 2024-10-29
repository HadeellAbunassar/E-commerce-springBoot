package com.example.Ecommerce.ExceptionHandling.CustomException;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
