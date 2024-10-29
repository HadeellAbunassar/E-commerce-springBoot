package com.example.Ecommerce.ExceptionHandling;


import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.ExceptionHandling.CustomException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exc) {
        ErrorResponse userError = new ErrorResponse();
        userError.setStatus(HttpStatus.NOT_FOUND.value());
        userError.setMessage(exc.getMessage());
        return new ResponseEntity<>(userError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Insufficientstockforproduct.class)
    public ResponseEntity<ErrorResponse> insufficientStock(Insufficientstockforproduct exc){
        ErrorResponse insSto = new ErrorResponse();
        insSto.setStatus(HttpStatus.BAD_REQUEST.value());
        insSto.setMessage((exc.getMessage()));
        return new ResponseEntity<>(insSto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> CartNotFoundException(CartNotFoundException exc){
        ErrorResponse insSto = new ErrorResponse();
        insSto.setStatus(HttpStatus.BAD_REQUEST.value());
        insSto.setMessage((exc.getMessage()));
        return new ResponseEntity<>(insSto,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> CategoryNotFoundException(CategoryNotFoundException exc){
        ErrorResponse insSto = new ErrorResponse();
        insSto.setStatus(HttpStatus.BAD_REQUEST.value());
        insSto.setMessage((exc.getMessage()));
        return new ResponseEntity<>(insSto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> OrderNotFoundException(OrderNotFoundException exc){
        ErrorResponse insSto = new ErrorResponse();
        insSto.setStatus(HttpStatus.BAD_REQUEST.value());
        insSto.setMessage((exc.getMessage()));
        return new ResponseEntity<>(insSto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> ProductNotFoundException(ProductNotFoundException exc){
        ErrorResponse insSto = new ErrorResponse();
        insSto.setStatus(HttpStatus.BAD_REQUEST.value());
        insSto.setMessage((exc.getMessage()));
        return new ResponseEntity<>(insSto,HttpStatus.BAD_REQUEST);
    }







}
