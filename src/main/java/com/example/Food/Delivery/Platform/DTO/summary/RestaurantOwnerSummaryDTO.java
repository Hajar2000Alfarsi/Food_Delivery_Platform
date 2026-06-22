package com.example.Food.Delivery.Platform.DTO.summary;

import com.example.Food.Delivery.Platform.Entities.RestaurantOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerSummaryDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public static RestaurantOwnerSummaryDTO fromEntity(RestaurantOwner owner) {
        if (owner == null) return null;
        RestaurantOwnerSummaryDTO dto = new RestaurantOwnerSummaryDTO();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setEmail(owner.getEmail());
        return dto;
    }
}
