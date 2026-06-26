package com.example.Food.Delivery.Platform.DTO.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCountDTO {
    private Integer restaurantId;
    private Long totalOrders;
}
