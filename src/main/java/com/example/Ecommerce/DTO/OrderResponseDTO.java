package com.example.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderResponseDTO { // for Admin Panel

    private String address;
    private String paymentDetails;
    private String city;
    private int postalCode;
    private String phoneNumber;
    private List<OrderItemDTO> orderItems;

    private Long id;
    private String status;
    private double totalPrice;

}
