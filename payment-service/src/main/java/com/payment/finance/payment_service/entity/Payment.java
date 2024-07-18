package com.payment.finance.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private Double amount;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private LocalDateTime refundedAt;


}