package com.example.Ecommerce.Controllers.UI;

import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.Role;
import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegisterController(UserServiceImpl userServiceImpl,
                              PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            model.addAttribute("validationErrors", errors);
            model.addAttribute("user", user);
            return "register";
        }

        if (userServiceImpl.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username is already taken.");
            return "register";
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setCart(new Cart());

        userServiceImpl.saveUser(user);
        return "loginPage";
    }

}
