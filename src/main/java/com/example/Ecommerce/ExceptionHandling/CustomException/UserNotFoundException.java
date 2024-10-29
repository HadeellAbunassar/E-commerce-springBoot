package com.example.Ecommerce.ExceptionHandling.CustomException;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }

}
