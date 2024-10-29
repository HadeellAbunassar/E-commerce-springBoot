package com.example.Ecommerce.Repositries;

import com.example.Ecommerce.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
