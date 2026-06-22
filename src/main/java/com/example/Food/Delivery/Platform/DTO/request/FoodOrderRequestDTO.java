package com.example.Food.Delivery.Platform.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderRequestDTO {
    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Restaurant ID")
    private Integer restaurantId;

    private String deliveryNotes;

    @NotEmpty
    @Valid
    private List<OrderItemRequestDTO> orderItems;
}
