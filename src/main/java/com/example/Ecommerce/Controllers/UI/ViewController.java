package com.example.Ecommerce.Controllers.UI;

import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    public String index(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!"anonymousUser".equals(username)) {
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                session.setAttribute("userId", user.getId());
            }
        }

        return "index";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkOut(){
        return "checkout";
    }


    @GetMapping("/done")
    public String finall(){
        return "done";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "Logged out successfully!";
    }
}
