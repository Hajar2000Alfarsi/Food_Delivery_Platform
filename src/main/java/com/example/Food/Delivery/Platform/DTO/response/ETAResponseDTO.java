package com.example.Food.Delivery.Platform.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ETAResponseDTO {
    private Integer orderId;

    private Double distanceKm;

    private Integer estimatedMinutes;
}
