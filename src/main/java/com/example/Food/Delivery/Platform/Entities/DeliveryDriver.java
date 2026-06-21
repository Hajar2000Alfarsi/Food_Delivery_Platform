package com.example.Food.Delivery.Platform.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDriver extends BaseUser{
    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;

    private Double currentLat;
    private Double currentLng;

    private Boolean isOnline;

    @OneToMany(mappedBy = "driver")
    private List<Delivery> deliveries;

    @OneToMany(mappedBy = "deliveryDriver")
    private List<Review> reviews;
}
