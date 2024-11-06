package com.example.Ecommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private String address;
    private Long userId;
    private PaymentDetails paymentDetails;
    private String city;
    private int postalCode;
    private String phoneNumber;


}
