package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.response.DeliveryResponseDTO;
import com.example.Food.Delivery.Platform.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    //Manually assign delivery
    @PostMapping("/order/{orderId}/assign-manual/{driverId}")
    public ResponseEntity<DeliveryResponseDTO> assignManual(
            @PathVariable Integer orderId,
            @PathVariable Integer driverId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.assignDriverToOrder(orderId, driverId));
    }

    //Auto assign
    @PostMapping("/order/{orderId}/assign-auto")
    public ResponseEntity<DeliveryResponseDTO> autoAssign(
            @PathVariable Integer orderId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryService.autoAssignDriver(orderId));
    }

    // Get delivery
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }
}
