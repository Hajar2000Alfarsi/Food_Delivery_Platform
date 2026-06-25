package com.example.Food.Delivery.Platform.DTO.request;

import com.example.Food.Delivery.Platform.Entities.ComboMeal;
import com.example.Food.Delivery.Platform.Entities.MenuItem;
import com.example.Food.Delivery.Platform.Repositories.MenuItemRepository;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboMealRequestDTO {
    @NotBlank(message = "Combo name is required")
    private String comboName;

    private String description;

    @NotNull(message = "Total Price Required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private Double totalPrice;

    @NotEmpty(message = "Combo must contain at least one menu item id")
    private List<Integer> menuItem;

    public ComboMeal toEntity(){
        ComboMeal comboMeal = new ComboMeal();
        comboMeal.setComboName(comboName);
        comboMeal.setDescription(description);
        comboMeal.setTotalPrice(totalPrice);

        return comboMeal;
    }
}
