package com.example.Food.Delivery.Platform.DTO.summary;

import com.example.Food.Delivery.Platform.DTO.response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAddressSummaryDTO {
    private Integer id;
    private String street;
    private String city;
    private Boolean isDefault;

    public static CustomerAddressSummaryDTO fromEntity(CustomerAddress address) {
        if (address == null) return null;

        return CustomerAddressSummaryDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .isDefault(address.getIsDefault())
                .build();
    }
}
