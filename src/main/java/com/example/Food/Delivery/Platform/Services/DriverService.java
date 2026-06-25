package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.DriverRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.DeliveryResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.DriverResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Delivery;
import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.DeliveryRepository;
import com.example.Food.Delivery.Platform.Repositories.DriverRepository;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired

    public DriverService(DriverRepository driverRepository, DeliveryRepository deliveryRepository) {
        this.driverRepository = driverRepository;
        this.deliveryRepository = deliveryRepository;
    }

    //Register Driver
    public DriverResponseDTO createDriver(DriverRequestDTO dto){
        DeliveryDriver driver = dto.toEntity();

        String driverCode = HelperUtils.generateCode("DRV");
        driver.setDriverCode(driverCode);

        driver.setIsOnline(false);
        driver.setCreatedDate(LocalDateTime.now());
        driver.setUpdatedDate(LocalDateTime.now());
        driver.setIsActive(true);

        DeliveryDriver saved = driverRepository.save(driver);

        return DriverResponseDTO.fromEntity(saved);
    }

    //get all driver
    public List<DriverResponseDTO> getAllDriver() {
        return driverRepository.findAllActive().stream().map(DriverResponseDTO::fromEntity).toList();
    }

    //Get online drivers
    public List<DriverResponseDTO> getOnlineDrivers() {

        return driverRepository.findByIsOnlineTrue()
                .stream()
                .map(DriverResponseDTO::fromEntity)
                .toList();
    }

    private DeliveryDriver findActiveDriver(Integer id) {

        return driverRepository.findByActiveID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));
    }

    //Toggle online status
    public DriverResponseDTO updateStatus(Integer id, boolean isOnline) {

        DeliveryDriver driver = findActiveDriver(id);

        driver.setIsOnline(isOnline);
        driver.setUpdatedDate(LocalDateTime.now());

        return DriverResponseDTO.fromEntity(driverRepository.save(driver));
    }

    //Update location
    public DriverResponseDTO updateLocation(Integer id, double lat, double lng) {

        DeliveryDriver driver = findActiveDriver(id);

        driver.setCurrentLat(lat);
        driver.setCurrentLng(lng);
        driver.setUpdatedDate(LocalDateTime.now());

        return DriverResponseDTO.fromEntity(driverRepository.save(driver));
    }

    //Delivery history
    public List<DeliveryResponseDTO> getDriverDeliveries(Integer id) {

        findActiveDriver(id);

        return deliveryRepository.findByActiveDriverId(id)
                .stream()
                .map(DeliveryResponseDTO::fromEntity)
                .toList();
    }

    //Active delivery
    public DeliveryResponseDTO getActiveDelivery(Integer id) {

        findActiveDriver(id);

        Delivery delivery = deliveryRepository.findInProgressByDriverId(id).orElseThrow(()->
                new ResourceNotFoundException("No active delivery found"));

        if (delivery == null) {
            throw new ResourceNotFoundException("No active delivery found");
        }

        return DeliveryResponseDTO.fromEntity(delivery);
    }
}
