package com.example.Ecommerce.Service.Order;
import com.example.Ecommerce.DTO.OrderDTO;
import com.example.Ecommerce.Entities.Order;

import java.util.List;

public interface OrderService {



        Order saveOrder(OrderDTO order);
        List<Order> getAllOrders();
        Order updateOrderStatus(String status, Long orderId);
        Order getOrderById(Long orderId);

}
