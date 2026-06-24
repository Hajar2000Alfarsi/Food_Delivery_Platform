package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.ComboMealRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.MenuItemRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.RestaurantRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.ComboMealResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.MenuItemResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.RestaurantResponseDTO;
import com.example.Food.Delivery.Platform.DTO.summary.MenuItemSummaryDTO;
import com.example.Food.Delivery.Platform.DTO.summary.RestaurantSummaryDTO;
import com.example.Food.Delivery.Platform.Entities.ComboMeal;
import com.example.Food.Delivery.Platform.Entities.MenuItem;
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
import java.util.List;

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

    //Get by cuisine type
    public List<RestaurantResponseDTO> getRestaurantsByCuisine(String cuisine) {

        return restaurantRepository.findByCuisineTypeIgnoreCase(cuisine)
                .stream()
                .map(RestaurantResponseDTO::fromEntity)
                .toList();
    }

    //Under delivery fee
    public List<RestaurantResponseDTO> getRestaurantsUnderDeliveryFee(double maxFee) {

        return restaurantRepository.findByDeliveryFeeLessThanEqual(maxFee)
                .stream()
                .map(RestaurantResponseDTO::fromEntity)
                .toList();
    }

    //Get restaurant menu
    public List<MenuItemResponseDTO> getMenuForRestaurant(Integer restaurantId) {

        findActiveRestaurant(restaurantId);

        return menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(MenuItemResponseDTO::fromEntity)
                .toList();
    }

    //Bulk price increase(Increase the price of all items in menu)
    public RestaurantSummaryDTO bulkUpdateMenuItemPrices(Integer restaurantId, double percentage) {

        Restaurant restaurant = findActiveRestaurant(restaurantId);

        List<MenuItem> items = menuItemRepository.findByRestaurantId(restaurantId);

        for (MenuItem item : items) {
            double newPrice = item.getPrice() + (item.getPrice() * percentage / 100);

            item.setPrice(newPrice);
            item.setUpdatedDate(LocalDateTime.now());
        }
        menuItemRepository.saveAll(items);

        return RestaurantSummaryDTO.fromEntity(restaurant);
    }

    //Add Menu Item
    public MenuItemResponseDTO addMenuItem(Integer restaurantId, MenuItemRequestDTO dto) {

        Restaurant restaurant = findActiveRestaurant(restaurantId);

        MenuItem item = dto.toEntity();
        item.setRestaurant(restaurant);
        item.setIsAvailable(true);

        return MenuItemResponseDTO.fromEntity(menuItemRepository.save(item));
    }

    //Toggle menu item availability
    public MenuItemSummaryDTO updateMenuItemAvailability(Integer itemId, boolean status) {

        MenuItem item = menuItemRepository.findByActiveId(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        item.setIsAvailable(status);
        item.setUpdatedDate(LocalDateTime.now());

        MenuItem saved = menuItemRepository.save(item);

        return MenuItemSummaryDTO.fromEntity(saved);
    }

    //Add Combo
    public ComboMealResponseDTO addCombo(Integer restaurantId, ComboMealRequestDTO dto) {

        Restaurant restaurant = findActiveRestaurant(restaurantId);

        ComboMeal combo = dto.toEntity();
        combo.setRestaurant(restaurant);

        return ComboMealResponseDTO.fromEntity(comboMealRepository.save(combo));
    }

    public List<RestaurantResponseDTO> getAllRestaurant() {
        return restaurantRepository.findAllActive().stream().map(RestaurantResponseDTO::fromEntity).toList();
    }

    public RestaurantResponseDTO findRestaurantByActiveID(Integer id){
        return restaurantRepository.findByActiveIdDTO(id).orElseThrow(()->
                new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    //get combo meal
    public List<ComboMealResponseDTO> getCombosForRestaurant(Integer restaurantId) {

        findActiveRestaurant(restaurantId);

        return comboMealRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(ComboMealResponseDTO::fromEntity)
                .toList();
    }




}
