package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseClass {
    private String name;
    private String description;
    private String cuisineType;

    private LocalTime openingTime;
    private LocalTime closingTime;

    private Double minOrderAmount;
    private Double deliveryFee;

    private Boolean acceptingOrders;

    @ManyToOne
    private RestaurantOwner restaurantOwner;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant")
    private List<ComboMeal> comboMeals;

    @OneToMany(mappedBy = "restaurant")
    private List<FoodOrder> orders;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

}
