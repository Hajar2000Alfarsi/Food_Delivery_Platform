package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.response.DeliveryResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Delivery;
import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.DeliveryRepository;
import com.example.Food.Delivery.Platform.Repositories.DriverRepository;
import com.example.Food.Delivery.Platform.Repositories.OrderRepository;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;

    @Autowired

    public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository, DriverRepository driverRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
    }

    //assign driver to delivery
    public DeliveryResponseDTO assignDriverToOrder(Integer orderId, Integer driverId) {
        FoodOrder order = orderRepository.findByActiveId(orderId).orElseThrow(()->
                new ResourceNotFoundException("Order not Found"));
        DeliveryDriver driver = driverRepository.findByActiveID(driverId).orElseThrow(()->
                new ResourceNotFoundException("Driver not found"));

        Delivery delivery = new Delivery();
        delivery.setTrackingCode(HelperUtils.generateCode("DEL"));
        delivery.setOrder(order);
        delivery.setDriver(driver);
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());
        delivery.setIsActive(true);

        return DeliveryResponseDTO.fromEntity(deliveryRepository.save(delivery));
    }

    //Auto assign driver (first online available)
    public DeliveryResponseDTO autoAssignDriver(Integer orderId){
        FoodOrder order = orderRepository.findByActiveId(orderId).orElseThrow(()->
                new ResourceNotFoundException("Order not Found"));
        DeliveryDriver driver = driverRepository.findFirstByIsOnlineTrueAndIsActiveTrue().orElseThrow(()->
                new ResourceNotFoundException("Driver not found"));

        Delivery delivery = new Delivery();
        delivery.setTrackingCode(HelperUtils.generateCode("DEL"));
        delivery.setOrder(order);
        delivery.setDriver(driver);
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());
        delivery.setIsActive(true);

        return DeliveryResponseDTO.fromEntity(deliveryRepository.save(delivery));
    }
}
