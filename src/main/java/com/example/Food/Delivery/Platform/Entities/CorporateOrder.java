package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateOrder extends BaseClass{
    private String corporateCode;
    private String companyName;
    private String costCenter;
    private String status;

    private LocalDateTime orderDate;

    private Double totalAmount;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(mappedBy = "corporateOrder")
    private List<CorporateOrderItem> items;
}
