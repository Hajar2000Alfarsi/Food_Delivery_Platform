package com.example.Food.Delivery.Platform.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverEarningsDTO {
    private Integer driverId;

    private Long completedDeliveries;

    private Double totalEarnings;
}
