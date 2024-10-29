package com.example.Ecommerce.Service.Cart.CartItem;

import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Product;

import java.util.List;

public interface CartItemService {
    List<CartItem> getCartItemsByProductId(Long productId);
    CartItem getCartItemByProductIdAndCartId(Long cartId, Long productId);
    Product getProductByCartItemId(Long cartItemId);
    void deleteCartItem(Long cartId, Long productId);
    void deleteAllCartItems(Long cartId);
}
