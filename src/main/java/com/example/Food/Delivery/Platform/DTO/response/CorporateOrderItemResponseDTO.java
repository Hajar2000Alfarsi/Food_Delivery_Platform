package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.DTO.summary.MenuItemSummaryDTO;
import com.example.Food.Delivery.Platform.Entities.CorporateOrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateOrderItemResponseDTO {
    private Integer id;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private String specialInstructions;
    private MenuItemSummaryDTO menuItem;

    public static CorporateOrderItemResponseDTO fromEntity(CorporateOrderItem item) {
        if (item == null) return null;

        CorporateOrderItemResponseDTO dto = new CorporateOrderItemResponseDTO();

        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setSpecialInstructions(item.getSpecialInstructions());
        dto.setMenuItem(MenuItemSummaryDTO.fromEntity(item.getMenuItem()));

        return dto;
    }

}
