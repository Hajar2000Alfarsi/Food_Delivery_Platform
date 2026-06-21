package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseClass{
    private String paymentMethod;
    private String status;
    private String transactionRef;

    private Double amount;

    private LocalDateTime processedAt;

    @OneToOne
    private FoodOrder order;
}
