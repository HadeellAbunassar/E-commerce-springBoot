package com.example.Ecommerce.Service.product;

import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Repositries.CartItemRepository;
import com.example.Ecommerce.Repositries.ProductRepository;
import com.example.Ecommerce.Service.Cart.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    @Lazy
    CartServiceImpl cartService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void deleteProduct(Long id) {
        List<CartItem> associatedCartItems = cartItemRepository.findCartItemsByProductId(id);

        Set<Long> cartIds = new HashSet<>();

        for (CartItem cartItem : associatedCartItems) {
                cartIds.add(cartItem.getCart().getId());
        }

        cartItemRepository.deleteAll(associatedCartItems);

        for (Long cartId : cartIds) {
            cartService.updateCartTotalPrice(cartService.findById(cartId));
        }

        productRepository.deleteById(id);


    }


    @Override
    public Product updateProduct(Product product, Long productId) {
        Product updatedProduct = productRepository.findById(productId).get();

        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setQuantity(product.getQuantity());

        List<CartItem> associatedCartItems = cartItemRepository.findCartItemsByProductId(productId);

        for (CartItem cartItem : associatedCartItems) {
            cartItem.setProduct(updatedProduct);
        }
        cartItemRepository.saveAll(associatedCartItems);


        for (CartItem cartItem : associatedCartItems) {
            cartService.updateCartTotalPrice(cartService.findById(cartItem.getCart().getId()));
        }


        return productRepository.save(updatedProduct);
    }









}
