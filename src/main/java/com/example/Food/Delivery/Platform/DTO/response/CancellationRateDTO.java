package com.example.Food.Delivery.Platform.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancellationRateDTO {
    private Long completedOrders;

    private Long cancelledOrders;

    private Double cancellationRate;
}
