package com.example.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class PaymentDetails {


    private BigDecimal amount;
    private BigDecimal refundedAmount;
    private String paymentMethod;

}
