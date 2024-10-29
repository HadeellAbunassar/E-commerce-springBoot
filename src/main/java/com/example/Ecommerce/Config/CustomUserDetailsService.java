package com.example.Ecommerce.Config;

import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.Entities.Role; // Ensure this import references your enum
import com.example.Ecommerce.Service.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority; // Ensure this import is included
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(mapRolesToAuthorities(user.getRoles())) // Assuming user.getRoles() returns Set<Role>
                .build();
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) { // Change to Set<Role>
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())) // Use enum's name() method
                .collect(Collectors.toList());
    }
}
