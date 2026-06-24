package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.RestaurantOwnerRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.RestaurantOwnerResponseDTO;
import com.example.Food.Delivery.Platform.Services.RestaurantOwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class RestaurantOwnerController {
    private final RestaurantOwnerService ownerService;

    @Autowired

    public RestaurantOwnerController(RestaurantOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //create owner
    @PostMapping
    public RestaurantOwnerResponseDTO createOwner(@Valid @RequestBody RestaurantOwnerRequestDTO dto){

        return ownerService.createOwner(dto);
    }

    //get owner by id
    @GetMapping("/{id}")
    public RestaurantOwnerResponseDTO getOwnerById(@PathVariable Integer id){

        return ownerService.getOwnerById(id);
    }

    //get all owners
    @GetMapping
    public List<RestaurantOwnerResponseDTO> getAllOwners(){

        return ownerService.getAllOwners();
    }

    @PutMapping("/{id}")
    public RestaurantOwnerResponseDTO updateOwner(@PathVariable Integer id, @Valid @RequestBody RestaurantOwnerRequestDTO dto){

        return ownerService.updateOwner(id,dto);
    }


}
