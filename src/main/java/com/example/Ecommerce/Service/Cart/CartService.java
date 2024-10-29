package com.example.Ecommerce.Service.Cart;

import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.CartItem;

import java.util.List;

public interface CartService {

    List<Cart> getAllCarts();

    Cart findById(Long id);

    void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    List<CartItem> viewAllItems(Long cartId);

    void removeAllItemsFromCart(Long cartId);

    Cart updateCart(Cart cart, Long cartId);
}
