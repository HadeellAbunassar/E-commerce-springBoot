package com.example.Ecommerce.DTO;


import com.example.Ecommerce.Entities.Cart;
import com.example.Ecommerce.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String address;
    private Long userId;
    private PaymentDetails paymentDetails;
    private String city;
    private int postalCode;
    private String phoneNumber;


}
