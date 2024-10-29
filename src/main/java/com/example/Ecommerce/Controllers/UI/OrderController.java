package com.example.Ecommerce.Controllers.UI;

import com.example.Ecommerce.DTO.OrderDTO;
import com.example.Ecommerce.DTO.OrderItemDTO;
import com.example.Ecommerce.DTO.OrderResponseDTO;
import com.example.Ecommerce.Entities.*;
import com.example.Ecommerce.Service.Order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;


    @PostMapping()
    public ResponseEntity<Order> CreateOrder(@RequestBody OrderDTO orderRequest){

        Order createdOrder = orderService.saveOrder(orderRequest);
     return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDTO> orderResponseDTOs = orders.stream()
                .map(order -> {
                    OrderResponseDTO dto = new OrderResponseDTO();
                    dto.setId(order.getId());
                    dto.setStatus(order.getStatus());
                    dto.setTotalPrice(order.getTotalPrice());
                    dto.setAddress(order.getAddress());
                    dto.setCity(order.getCity());
                    dto.setPhoneNumber(order.getPhoneNumber());
                    dto.setPaymentDetails(order.getPayment().getPaymentMethod());
                    dto.setOrderItems(order.getOrderItems().stream()
                            .map(item -> new OrderItemDTO(item.getId(), item.getProduct().getId(), item.getQuantity()))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderResponseDTOs, HttpStatus.OK);
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(status, orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.getOrderById(orderId) , HttpStatus.OK);
    }

}
