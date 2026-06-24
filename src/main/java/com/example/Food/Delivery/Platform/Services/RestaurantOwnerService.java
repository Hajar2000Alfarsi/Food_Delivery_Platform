package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.RestaurantOwnerRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.RestaurantOwnerResponseDTO;
import com.example.Food.Delivery.Platform.Entities.RestaurantOwner;
import com.example.Food.Delivery.Platform.Exceptions.DuplicateResourceException;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.RestaurantOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantOwnerService {
    private final RestaurantOwnerRepository ownerRepository;

    @Autowired

    public RestaurantOwnerService(RestaurantOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    //create owner
    public RestaurantOwnerResponseDTO createOwner(RestaurantOwnerRequestDTO dto){

        ownerRepository.getByEmail(dto.getEmail()).orElseThrow(()->
            new DuplicateResourceException("Email already exists"));

        RestaurantOwner owner = new RestaurantOwner();

        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmail(dto.getEmail());
        owner.setPhoneNumber(dto.getPhone());

        owner.setPasswordHash(dto.getPassword());

        owner.setBusinessLicenseCode(dto.getBusinessLicenseCode());

        RestaurantOwner saved = ownerRepository.save(owner);

        return RestaurantOwnerResponseDTO.fromEntity(saved);
    }

    //get by owner id
    public RestaurantOwnerResponseDTO getOwnerById(Integer id){

        RestaurantOwner owner = ownerRepository.getOwnerById(id).orElseThrow(() ->
                                new ResourceNotFoundException("Owner not found"));

        return RestaurantOwnerResponseDTO.fromEntity(owner);
    }

    //get all
    public List<RestaurantOwnerResponseDTO> getAllOwners(){

        return ownerRepository.getAllOwners()
                .stream()
                .map(RestaurantOwnerResponseDTO::fromEntity)
                .toList();
    }

    //update owner
    public RestaurantOwnerResponseDTO updateOwner(Integer id, RestaurantOwnerRequestDTO dto){

        RestaurantOwner owner = ownerRepository.getOwnerById(id).orElseThrow(() ->
                                new ResourceNotFoundException("Owner not found"));

        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmail(dto.getEmail());
        owner.setPhoneNumber(dto.getPhone());
        owner.setBusinessLicenseCode(dto.getBusinessLicenseCode());

        RestaurantOwner updated = ownerRepository.save(owner);

        return RestaurantOwnerResponseDTO.fromEntity(updated);
    }

    //delete owner
    public String deleteOwner(Integer id){

        RestaurantOwner owner = ownerRepository.getOwnerById(id).orElseThrow(() ->
                                new ResourceNotFoundException("Owner not found"));

        owner.setIsActive(false);
        ownerRepository.save(owner);

        return "Owner deleted successfully";
    }

}
