package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.ComboMeal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboMealResponseDTO {
    private Integer id;
    private String comboName;
    private String description;
    private Double totalPrice;
    private Boolean isAvailable;
    private List<MenuItemResponseDTO> items;

    public static ComboMealResponseDTO fromEntity(ComboMeal meal) {
        if (meal == null) return null;

        return ComboMealResponseDTO.builder()
                .id(meal.getId())
                .comboName(meal.getComboName())
                .description(meal.getDescription())
                .totalPrice(meal.getTotalPrice())
                .isAvailable(true)
                .build();
    }
}
