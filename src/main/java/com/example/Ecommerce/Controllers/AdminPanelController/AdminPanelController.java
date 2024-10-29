package com.example.Ecommerce.Controllers.AdminPanelController;

import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Entities.User;
import com.example.Ecommerce.Service.Category.CategoryServiceImpl;
import com.example.Ecommerce.Service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryServiceImpl categoryService;


    @GetMapping
    public String welcomeAdmin(Model model) {
        model.addAttribute("user", new User());
        return "AdminPanel/admin";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("products", productList);
        model.addAttribute("newProduct", new Product());
        return "AdminPanel/products";
    }

    @GetMapping("/category")
    public String categories(Model model) {
        List<Category> categoryList = categoryService.fetchCategoryList();
        model.addAttribute("categories", categoryList);
        return "AdminPanel/category";
    }

    @GetMapping("/users")
    public String users(){
        return "AdminPanel/users";
    }


    @GetMapping("/orders")
    public String orders(){
        return "AdminPanel/orders";
    }





}
