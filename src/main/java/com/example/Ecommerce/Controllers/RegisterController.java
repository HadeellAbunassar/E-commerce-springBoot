package com.example.Ecommerce.Controllers;


import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    private  UserServiceImpl userServiceImpl;


    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody()  User user,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (userServiceImpl.existsByUsername(user.getUsername())) {
          return ResponseEntity.badRequest().body("User name already taken");
        }

        userServiceImpl.saveUser(user);

        return ResponseEntity.ok().body("User added successfully");

    }

}
