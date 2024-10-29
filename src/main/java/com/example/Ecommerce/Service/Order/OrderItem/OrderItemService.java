package com.example.Ecommerce.Service.Order.OrderItem;


import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderItem;

import java.util.List;

public interface OrderItemService {

     List<OrderItem> moveCartItemToOrderItems(List<CartItem> cartItemList, Order order);

}
