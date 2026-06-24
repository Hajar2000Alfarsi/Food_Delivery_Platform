package com.example.Food.Delivery.Platform.DTO.request;

import com.example.Food.Delivery.Platform.Entities.CorporateOrder;
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
public class CorporateOrderRequestDTO {
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Cost center is required")
    private String costCenter;

    @NotNull(message = "Restaurant ID is required")
    private Integer restaurantId;

    @NotEmpty(message = "Order must contain items")
    private List<OrderItemRequestDTO> items;

    public CorporateOrder toEntity(){
        CorporateOrder corporateOrder = new CorporateOrder();
        corporateOrder.setCompanyName(companyName);
        corporateOrder.setCostCenter(costCenter);

        return corporateOrder;
    }
}
