package com.example.Food.Delivery.Platform.DTO.request;

import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressRequestDTO {
    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Building Information is required")
    private String building;

    @NotBlank(message = "Default status must be specified")
    private Boolean isDefault;

    public CustomerAddress toEntity(){
       CustomerAddress address = new CustomerAddress();
       address.setStreet(street);
       address.setCity(city);
       address.setBuilding(building);
       address.setIsDefault(isDefault);

       return address;
    }

    //update
    public void applayTo(CustomerAddress address){
        address.setStreet(this.street);
        address.setCity(this.city);
        address.setBuilding(this.building);
        address.setIsDefault(this.isDefault);
    }
}
