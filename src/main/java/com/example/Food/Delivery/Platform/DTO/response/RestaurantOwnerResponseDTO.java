package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.RestaurantOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String businessLicenseCode;

    public static RestaurantOwnerResponseDTO fromEntity(RestaurantOwner owner) {
        if (owner == null) return null;

        RestaurantOwnerResponseDTO dto = new RestaurantOwnerResponseDTO();

        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setEmail(owner.getEmail());
        dto.setPhone(owner.getPhoneNumber());
        dto.setBusinessLicenseCode(owner.getBusinessLicenseCode());

        return dto;
    }
}
