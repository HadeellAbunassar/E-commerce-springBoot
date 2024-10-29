package com.example.Ecommerce.ExceptionHandling.CustomException;

public class Insufficientstockforproduct extends RuntimeException {

    public Insufficientstockforproduct(String message) {
        super(message);
    }

}
