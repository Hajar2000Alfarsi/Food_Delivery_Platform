package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem extends BaseClass {
    private String name;
    private String description;
    private Double price;

    private Boolean isAvailable;
    private Boolean isVegetarian;

    private Integer calories;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "menuItems")
    private List<ComboMeal> comboMeals;

    @OneToMany(mappedBy = "menuItems")
    private  List<OrderItem> orderItems;
}
