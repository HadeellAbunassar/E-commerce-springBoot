package com.example.Ecommerce.Controllers;

import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Service.Cart.CartService;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final UserServiceImpl userService;

    @Autowired
    public CartController(CartService cartService,UserServiceImpl userService)
    {
        this.cartService = cartService;
        this.userService=userService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.findById(cartId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItem>> viewAllItems(@PathVariable Long userId) {
        Cart cart = userService.getById(userId).getCart();
        List<CartItem> items = cartService.viewAllItems(cart.getId());

        return ResponseEntity.ok(items);
    }

    @PostMapping("/{userId}/items/{productId}")
    public ResponseEntity<String> addItemToCart(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        Cart cart = userService.getById(userId).getCart();
        cartService.addItemToCart(cart.getId(), productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart successfully");
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        Cart cart = userService.getById(userId).getCart();
        cartService.removeItemFromCart(cart.getId(), productId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }
}
