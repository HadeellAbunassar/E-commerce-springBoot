package com.example.Ecommerce.ExceptionHandling.CustomException;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
