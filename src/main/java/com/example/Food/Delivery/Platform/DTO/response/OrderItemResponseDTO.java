package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.DTO.summary.MenuItemSummaryDTO;
import com.example.Food.Delivery.Platform.Entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private Integer id;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private String specialInstructions;

    private MenuItemSummaryDTO menuItem;

    public static OrderItemResponseDTO fromEntity(OrderItem item) {
        if (item == null) return null;

        OrderItemResponseDTO dto = new OrderItemResponseDTO();

        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setSpecialInstructions(item.getSpecialInstructions());

        dto.setMenuItem(MenuItemSummaryDTO.fromEntity(item.getMenuItem()));

        return dto;

    }
}
