package com.example.Food.Delivery.Platform.DTO.summary;

import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverSummaryDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String driverCode;

    public static DriverSummaryDTO fromEntity(DeliveryDriver driver) {
        if (driver == null) return null;
        DriverSummaryDTO dto = new DriverSummaryDTO();
        dto.setId(driver.getId());
        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setPhone(driver.getPhoneNumber());
        dto.setDriverCode(driver.getDriverCode());
        return dto;
    }

}
