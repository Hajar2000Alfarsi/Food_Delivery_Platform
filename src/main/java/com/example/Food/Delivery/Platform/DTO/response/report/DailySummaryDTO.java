package com.example.Food.Delivery.Platform.DTO.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySummaryDTO {
    private String date;

    private Long totalOrders;

    private Double totalRevenue;

    private Double averageOrderValue;

    private Double cancellationRate;

    private Double totalDeliveryFees;
}
