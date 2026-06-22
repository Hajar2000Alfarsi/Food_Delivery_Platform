package com.example.Food.Delivery.Platform.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CorporateOrderItemRequestDTO {
    @NotNull(message = "MenuItem ID is required")
    private Integer menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String specialInstructions;
}
