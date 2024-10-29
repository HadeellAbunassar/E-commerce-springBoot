package com.example.Ecommerce.Repositries;

import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id = ?1")
    List<CartItem> findCartItemsByProductId(Long productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id = ?2 AND EXISTS (SELECT 1 FROM Cart c WHERE c.id = ?1)")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);


    @Query("SELECT ci.product FROM CartItem ci WHERE ci.id = ?1")
    Product findProductByCartItemId(Long cartItemId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.product.id = ?2 AND ci.cart.id = ?1")
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1")
    void deleteAllCartItemsByCartId(Long cartId);


}