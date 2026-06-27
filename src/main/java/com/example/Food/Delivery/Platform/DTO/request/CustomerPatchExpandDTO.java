package com.example.Food.Delivery.Platform.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPatchExpandDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
