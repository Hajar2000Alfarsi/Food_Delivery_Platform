package com.example.Food.Delivery.Platform.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAnalyticsDTO {
    private Integer restaurantId;

    private Double averageRating;

    private Long totalCompletedOrders;

    private Double totalRevenue;
}
