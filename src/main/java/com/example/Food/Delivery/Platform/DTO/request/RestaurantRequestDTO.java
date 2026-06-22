package com.example.Food.Delivery.Platform.DTO.request;

import com.example.Food.Delivery.Platform.Entities.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDTO {
    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Cuisine type is required")
    private String cuisineType;

    @NotBlank(message = "Opening time is required (HH:mm)")
    private LocalTime openingTime;

    @NotBlank(message = "Closing time is required (HH:mm)")
    private LocalTime closingTime;

    @NotNull(message = "Minimum order amount is required")
    @PositiveOrZero(message = "Minimum order amount must be positive or zero")
    private Double minOrderAmount;

    @NotNull(message = "Delivery fee is required")
    @PositiveOrZero(message = "Delivery fee must be positive or zero")
    private Double deliveryFee;

    public Restaurant toEntity(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setDescription(description);
        restaurant.setCuisineType(cuisineType);
        restaurant.setOpeningTime(openingTime);
        restaurant.setClosingTime(closingTime);
        restaurant.setMinOrderAmount(minOrderAmount);
        restaurant.setDeliveryFee(deliveryFee);
        restaurant.setAcceptingOrders(true);
        return restaurant;
    }
}
