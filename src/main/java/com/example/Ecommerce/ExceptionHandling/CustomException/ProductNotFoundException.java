package com.example.Ecommerce.ExceptionHandling.CustomException;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
