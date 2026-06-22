package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Integer calories;

    public static MenuItemResponseDTO fromEntity(MenuItem item) {
        if (item == null) return null;

        return MenuItemResponseDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .isAvailable(item.getIsAvailable())
                .isVegetarian(item.getIsVegetarian())
                .calories(item.getCalories())
                .build();
    }
}
