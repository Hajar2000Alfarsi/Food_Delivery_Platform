package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.RestaurantRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.RestaurantResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Entities.RestaurantOwner;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.ComboMealRepository;
import com.example.Food.Delivery.Platform.Repositories.MenuItemRepository;
import com.example.Food.Delivery.Platform.Repositories.RestaurantOwnerRepository;
import com.example.Food.Delivery.Platform.Repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final ComboMealRepository comboMealRepository;
    private final RestaurantOwnerRepository ownerRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ComboMealRepository comboMealRepository, RestaurantOwnerRepository ownerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.comboMealRepository = comboMealRepository;
        this.ownerRepository = ownerRepository;
    }

    //Create Restaurant
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, Integer ownerId) {

        RestaurantOwner owner = ownerRepository.findByActiveId(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Restaurant restaurant = dto.toEntity();

        restaurant.setRestaurantOwner(owner);
        restaurant.setAcceptingOrders(true);
        restaurant.setCreatedDate(LocalDateTime.now());
        restaurant.setUpdatedDate(LocalDateTime.now());
        restaurant.setIsActive(true);

        Restaurant saved = restaurantRepository.save(restaurant);

        return RestaurantResponseDTO.fromEntity(saved);
    }

    //Find Active Restaurant
    private Restaurant findActiveRestaurant(Integer id) {

        return restaurantRepository.findByActiveId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
    }

    //Toggle accepting orders
    public RestaurantResponseDTO toggleAcceptingOrders(Integer restaurantId, boolean status) {

        Restaurant restaurant = findActiveRestaurant(restaurantId);

        restaurant.setAcceptingOrders(status);
        restaurant.setUpdatedDate(LocalDateTime.now());

        return RestaurantResponseDTO.fromEntity(restaurantRepository.save(restaurant));
    }

    //Update delivery fee
    public RestaurantResponseDTO updateDeliveryFee(Integer restaurantId, double newFee) {

        Restaurant restaurant = findActiveRestaurant(restaurantId);

        restaurant.setDeliveryFee(newFee);
        restaurant.setUpdatedDate(LocalDateTime.now());

        return RestaurantResponseDTO.fromEntity(restaurantRepository.save(restaurant));
    }




}
