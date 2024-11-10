package com.example.Ecommerce.Controllers;


import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    private  UserServiceImpl userServiceImpl;


    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody()  User user) {

        if (userServiceImpl.existsByUsername(user.getUsername())) {
          return ResponseEntity.badRequest().body("User name already taken");
        }

        userServiceImpl.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorMessages);
    }

}
