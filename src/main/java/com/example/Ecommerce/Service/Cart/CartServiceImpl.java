package com.example.Ecommerce.Service.Cart;

import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.ExceptionHandling.CustomException.Insufficientstockforproduct;
import com.example.Ecommerce.Repositries.CartItemRepository;
import com.example.Ecommerce.Repositries.CartRepository;
import com.example.Ecommerce.Service.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(ProductService productService,
                           CartRepository cartRepository,
                           CartItemRepository cartItemRepository) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public List<CartItem> viewAllItems(Long cartId) {
        Cart cart = findById(cartId);
        return cart.getCartItems();
    }

       @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = findById(cartId);
        Product product = productService.findById(productId);

        if (product.getQuantity() < quantity) {
            throw new Insufficientstockforproduct("Insufficient stock for product");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cart.getCartItems().add(newCartItem);
            newCartItem.setCart(cart);
            cartItemRepository.save(newCartItem);
        }

        updateCartTotalPrice(cart);
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = findById(cartId);

        CartItem existingCartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (existingCartItem != null) {
            int currentQuantity = existingCartItem.getQuantity();

            if (currentQuantity > 1) {
                existingCartItem.setQuantity(currentQuantity - 1);
                cartItemRepository.save(existingCartItem);
            } else {
                cartItemRepository.deleteCartItemByCartIdAndProductId(cartId,productId);
            }
        }

        updateCartTotalPrice(cart);
        updateCart(cart,cartId);
    }

    public void updateCartTotalPrice(Cart cart) {
        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }

    @Override
    @Transactional
    public void removeAllItemsFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cart.getCartItems();
        cartItemRepository.deleteAll(cartItems);
    }

    @Override
    public Cart updateCart(Cart cart, Long cartId) {
        Cart cartDB = cartRepository.findById(cartId).get();
        cartDB.setCartItems(cart.getCartItems());
        cartDB.setTotalPrice(cart.getTotalPrice());
        return cartRepository.save(cartDB);
    }


}
