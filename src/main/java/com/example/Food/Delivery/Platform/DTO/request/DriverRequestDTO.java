package com.example.Food.Delivery.Platform.DTO.request;

import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    private String phone;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotBlank(message = "Vehicle plate number is required")
    private String vehiclePlate;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    public DeliveryDriver toEntity(){
        DeliveryDriver driver = new DeliveryDriver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setEmail(email);
        driver.setPhoneNumber(phone);
        driver.setPasswordHash(password);
        driver.setVehicleType(vehicleType);
        driver.setVehiclePlate(vehiclePlate);
        driver.setCurrentLat(latitude);
        driver.setCurrentLng(longitude);
        return driver;
    }
}
