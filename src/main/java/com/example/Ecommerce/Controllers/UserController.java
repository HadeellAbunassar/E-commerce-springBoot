package com.example.Ecommerce.Controllers;

import com.example.Ecommerce.DTO.UserDTO;
import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.Role;
import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.ExceptionHandling.CustomException.UserNotFoundException;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userServiceImpl.fetchUserList();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        new ArrayList<>(user.getRoles())
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }


    @GetMapping("/{id}") //done
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId) {
        List<User> users = userServiceImpl.fetchUserList();

        if  (userId < 0 || userId > users.size()) {
            throw new UserNotFoundException("The user is not found");
        }

        User user = userServiceImpl.getById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/{id}") //done
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable("id") Long userId) {
        User updatedUser = userServiceImpl.updateUser(user, userId , passwordEncoder);

        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //done
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long userId) {
        userServiceImpl.deleteUserById(userId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
    }

}