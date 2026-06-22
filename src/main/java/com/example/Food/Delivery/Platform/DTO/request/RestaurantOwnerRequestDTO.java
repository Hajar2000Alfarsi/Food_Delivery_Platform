package com.example.Food.Delivery.Platform.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Phone number must be between 8 and 15 digits")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password; // سيتم تشفيرها في الـ Service قبل تحويلها للـ Entity

    @NotBlank(message = "Business license code is required")
    private String businessLicenseCode;

    /*public RestaurantOwner toEntity(String passwordHash) {
        RestaurantOwner owner = new RestaurantOwner();
        owner.setFirstName(this.firstName);
        owner.setLastName(this.lastName);
        owner.setEmail(this.email);
        owner.setPhone(this.phone);
        owner.setPasswordHash(passwordHash);
        owner.setBusinessLicenseCode(this.businessLicenseCode);
        return owner;*/
}
