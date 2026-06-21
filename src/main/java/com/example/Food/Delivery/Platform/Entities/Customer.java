package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseUser{
    private Integer loyaltyPoints;
    private String customerCode;

    @OneToMany(mappedBy = "customer")
    private  List<CustomerAddress> addresses;

    @OneToMany(mappedBy = "customer")
    private  List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

}
