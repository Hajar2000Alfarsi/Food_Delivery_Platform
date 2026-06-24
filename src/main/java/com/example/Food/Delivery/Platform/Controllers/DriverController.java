package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.DriverRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.DriverResponseDTO;
import com.example.Food.Delivery.Platform.Services.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/derivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    //Register driver
    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@Valid @RequestBody DriverRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.createDriver(dto));
    }

    //Get all drivers
    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDriver());
    }

    //Get online drivers
    @GetMapping("/online")
    public ResponseEntity<List<DriverResponseDTO>> getOnlineDrivers() {
        return ResponseEntity.ok(driverService.getOnlineDrivers());
    }

    //Toggle status
    @PutMapping("/{id}/status")
    public ResponseEntity<DriverResponseDTO> updateStatus(@PathVariable Integer id, @RequestParam boolean isOnline) {
        return ResponseEntity.ok(driverService.updateStatus(id, isOnline));
    }

    //Update location
    @PutMapping("{id}/location")
    public ResponseEntity<DriverResponseDTO> updateLocation(
            @PathVariable Integer id,
            @RequestParam double lat,
            @RequestParam double lng){
        return ResponseEntity.ok(driverService.updateLocation(id, lat, lng));
    }



}
