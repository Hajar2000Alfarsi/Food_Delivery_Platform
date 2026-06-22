package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer loyaltyPoints;
    private String customerCode;
    private List<CustomerAddressResponseDTO> address;

    public static CustomerResponseDTO fromEntity(Customer customer) {
        if (customer == null) {
            return null;
        }

        List<CustomerAddressResponseDTO> addressDTOs = null;
        if (customer.getAddresses() != null) {
            addressDTOs = customer.getAddresses().stream().filter(addr -> addr.getIsActive())
                    .map(CustomerAddressResponseDTO::fromEntity).collect(Collectors.toList());
        }
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .loyaltyPoints(customer.getLoyaltyPoints())
                .customerCode(customer.getCustomerCode())
                .address(addressDTOs)
                .build();
    }
}
