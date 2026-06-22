package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;
    private Boolean isOnline;
    private Double currentLat;
    private Double currentLng;

    public static DriverResponseDTO fromEntity(DeliveryDriver driver) {
        if (driver == null) return null;
        DriverResponseDTO dto = new DriverResponseDTO();
        dto.setId(driver.getId());
        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setEmail(driver.getEmail());
        dto.setPhone(driver.getPhoneNumber());
        dto.setDriverCode(driver.getDriverCode());
        dto.setVehicleType(driver.getVehicleType());
        dto.setVehiclePlate(driver.getVehiclePlate());
        dto.setIsOnline(driver.getIsOnline());
        dto.setCurrentLat(driver.getCurrentLat());
        dto.setCurrentLng(driver.getCurrentLng());
        return dto;
    }
}
