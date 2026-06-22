package com.example.Food.Delivery.Platform.DTO.summary;

import com.example.Food.Delivery.Platform.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSummaryDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String customerCode;

    public static CustomerSummaryDTO fromEntity(Customer customer){
        if (customer == null) {return  null;}

        return CustomerSummaryDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .customerCode(customer.getCustomerCode())
                .build();
    }
}
