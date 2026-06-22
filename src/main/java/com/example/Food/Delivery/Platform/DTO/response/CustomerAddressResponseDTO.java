package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAddressResponseDTO {
    private Integer id;
    private String street;
    private String city;
    private String building;
    private Boolean isDefault;

    public static CustomerAddressResponseDTO fromEntity(CustomerAddress address){
        if (address == null) return null;

        return CustomerAddressResponseDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .building(address.getBuilding())
                .isDefault(address.getIsDefault())
                .build();
    }
}
