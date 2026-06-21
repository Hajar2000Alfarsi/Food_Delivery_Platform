package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RestaurantOwner extends BaseUser{
    private String businessLicenseCode;

    @OneToMany(mappedBy = "restaurantOwner")
    private List<Restaurant> restaurants;
}
