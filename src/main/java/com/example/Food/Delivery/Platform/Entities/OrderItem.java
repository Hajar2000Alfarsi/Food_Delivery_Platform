package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseClass{
    private Integer quantity;

    private Double unitPrice;
    private Double totalPrice;

    private String specialInstructions;

    @ManyToOne
    private Order order;

    @ManyToOne
    private MenuItem menuItem;

}
