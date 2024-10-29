package com.example.Ecommerce.Service.User;

import com.example.Ecommerce.Entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    List<User> fetchUserList();

    void deleteUserById(Long UserId);

    User updateUser(User user, Long userId, PasswordEncoder passwordEncoder);

    User getById(Long userId);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

     User getUserByCartId(Long cartId);
}






