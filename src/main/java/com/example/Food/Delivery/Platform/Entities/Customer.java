package com.example.Food.Delivery.Platform.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private  List<CustomerAddress> addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private  List<FoodOrder> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

}
