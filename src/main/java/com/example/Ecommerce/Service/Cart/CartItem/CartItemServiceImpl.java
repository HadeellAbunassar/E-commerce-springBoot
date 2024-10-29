package com.example.Ecommerce.Service.Cart.CartItem;

import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Repositries.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> getCartItemsByProductId(Long productId) {
        return cartItemRepository.findCartItemsByProductId(productId);
    }

    @Override
    public CartItem getCartItemByProductIdAndCartId(Long cartId, Long productId) {
        return cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
    }

    @Override
    public Product getProductByCartItemId(Long cartItemId) {
        return cartItemRepository.findProductByCartItemId(cartItemId);
    }

    @Override
    public void deleteCartItem(Long cartId, Long productId) {
        cartItemRepository.deleteCartItemByCartIdAndProductId(cartId, productId);
    }

    @Transactional
    @Override
    public void deleteAllCartItems(Long cartId) {
        cartItemRepository.deleteAllCartItemsByCartId(cartId);
    }
}
