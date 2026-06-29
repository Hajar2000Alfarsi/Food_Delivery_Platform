package com.example.Food.Delivery.Platform.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusiestHourDTO {
    private Integer hour;

    private Long totalOrders;
}
