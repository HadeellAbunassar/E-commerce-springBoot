package com.example.Ecommerce.Service.User;

import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.Role;
import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.ExceptionHandling.CustomException.UserNotFoundException;
import com.example.Ecommerce.Repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Override
    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setCart(new Cart());

        return userRepository.save(user);
    }


    @Override
    public List<User> fetchUserList() {
        return (List<User>) userRepository.findAll();
    }


    @Override
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Clear the user's roles
        user.getRoles().clear();

        // Save changes to ensure the associations are removed
        userRepository.save(user);

        // Now delete the user
        userRepository.deleteById(userId);
    }


    @Override
    public User updateUser(User user, Long userId,PasswordEncoder passwordEncoder) {
        User userDB = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (Objects.nonNull(user.getEmail()) && !user.getEmail().trim().isEmpty()) {
            userDB.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getUsername()) && !user.getUsername().trim().isEmpty()) {
            userDB.setUsername(user.getUsername());
        }
        if (Objects.nonNull(user.getPassword()) && !user.getPassword().trim().isEmpty()) {
            userDB.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt the password
        }

        if (user.getRoles() != null) {
            userDB.getRoles().clear();
            for (Role role : user.getRoles()) {
                userDB.getRoles().add(role);
            }
        }

        return userRepository.save(userDB);
    }


    @Override
    public User getById(Long userId){
        return userRepository.findById(userId).get();
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User getUserByCartId(Long cartId) {
        return userRepository.findByCart_Id(cartId).get();
    }

}




