package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseClass{
    private String targetType;
    private Integer rating;
    private String comment;

    private LocalDateTime createdAt;

    @ManyToOne
    private Customer customer;

    @ManyToOne(optional = true)
    private Restaurant restaurant;

    @ManyToOne(optional = true)
    private DeliveryDriver deliveryDriver;
}
