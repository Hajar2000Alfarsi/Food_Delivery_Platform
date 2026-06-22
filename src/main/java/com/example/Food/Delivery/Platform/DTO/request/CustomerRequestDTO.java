package com.example.Food.Delivery.Platform.DTO.request;

import com.example.Food.Delivery.Platform.Entities.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDTO {
    @NotBlank(message = "First Name required")
    private String firstName;

    @NotBlank(message = "Last Name required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Phone Number required")
    @Pattern(regexp = "^//+?[0-9]{8,15}$", message = "Phone Number must be between 8 and 15 duguts and can start with +")
    private String phoneNumber;

    @NotBlank(message = "password is required")
    private String passwordHash;

    public Customer toEntity(String passwordHash, String customerCode){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastname);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setPasswordHash(passwordHash);
        customer.setCustomerCode(customerCode);
        customer.setLoyaltyPoints(0);

        return customer;
    }

}
