package com.example.Food.Delivery.Platform.DTO.summary;

import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
    private Integer id;
    private String orderCode;
    private String status;
    private Double totalAmount;

    public static OrderSummaryDTO fromEntity(FoodOrder order) {
        if (order == null) return null;
        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setId(order.getId());
        dto.setOrderCode(order.getOrderCode());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        return dto;
    }
}
