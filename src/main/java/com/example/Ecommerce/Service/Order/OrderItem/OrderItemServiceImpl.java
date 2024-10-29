package com.example.Ecommerce.Service.Order.OrderItem;

import com.example.Ecommerce.Entities.CartItem;
import com.example.Ecommerce.Entities.Order;
import com.example.Ecommerce.Entities.OrderItem;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{


    @Override
    public List<OrderItem> moveCartItemToOrderItems(List<CartItem> cartItemList,Order order) {


        List<OrderItem> newList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();

        for(CartItem cartItem : cartItemList){
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            newList.add(orderItem);
        }
        return newList;
    }


}
