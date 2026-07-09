package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.ComboMealRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.MenuItemRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.RestaurantRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.*;
import com.example.Food.Delivery.Platform.DTO.summary.MenuItemSummaryDTO;
import com.example.Food.Delivery.Platform.DTO.summary.RestaurantSummaryDTO;
import com.example.Food.Delivery.Platform.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    //Create restaurant
    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@PathVariable Integer ownerId,
                                                                       @Valid @RequestBody RestaurantRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(dto, ownerId));
    }

    //Get all restaurants
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAll() {
        return ResponseEntity.ok(restaurantService.getAllRestaurant());
    }

    //Get by id
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.findRestaurantByActiveID(id));
    }

    //Cuisine filter
    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RestaurantResponseDTO>> getByCuisine(@PathVariable String cuisine) {

        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisine(cuisine));
    }

    //Toggle orders
    @PutMapping("/{id}/toggle-orders")
    public ResponseEntity<RestaurantResponseDTO> toggleOrders(@PathVariable Integer id, @RequestParam boolean accepting) {

        return ResponseEntity.ok(restaurantService.toggleAcceptingOrders(id, accepting));
    }

    //Update fee
    @PutMapping("/{id}/fee/{newFee}")
    public ResponseEntity<RestaurantResponseDTO> updateFee(@PathVariable Integer id, @PathVariable double newFee) {

        return ResponseEntity.ok(restaurantService.updateDeliveryFee(id, newFee));
    }

    //Get menu
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenu(@PathVariable Integer id) {

        return ResponseEntity.ok(restaurantService.getMenuForRestaurant(id));
    }

    //get combo meals
    @GetMapping("/{id}/combos")
    public ResponseEntity<List<ComboMealResponseDTO>> getCombos(@PathVariable Integer id) {

        return ResponseEntity.ok(restaurantService.getCombosForRestaurant(id));
    }

    //Add menu item
    @PostMapping("/{id}/menu")
    public ResponseEntity<MenuItemResponseDTO> addMenu(@PathVariable Integer id, @Valid @RequestBody MenuItemRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addMenuItem(id, dto));
    }

    //Update menu availability
    @PutMapping("/menu/{itemId}/available")
    public ResponseEntity<MenuItemSummaryDTO> updateAvailability(@PathVariable Integer itemId, @RequestParam boolean status) {

        return ResponseEntity.ok(restaurantService.updateMenuItemAvailability(itemId, status));
    }

    //Add combo meal
    @PostMapping("/{id}/combos")
    public ResponseEntity<ComboMealResponseDTO> addCombo(@PathVariable Integer id, @Valid @RequestBody ComboMealRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addCombo(id, dto));
    }

    //Bulk price increase
    @PutMapping("/{id}/bulk-price-increase")
    public ResponseEntity<RestaurantSummaryDTO> bulkIncrease(@PathVariable Integer id, @RequestParam double percentage) {

        return ResponseEntity.ok(restaurantService.bulkUpdateMenuItemPrices(id, percentage));
    }

    //Restaurants within a radius
    @GetMapping("/near")
    public ResponseEntity<List<RestaurantResponseDTO>> getNearbyRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radiusKm) {

        return ResponseEntity.ok(restaurantService.getNearbyRestaurants(lat, lng, radiusKm));
    }

    //Average rating, total revenue, total completed orders(Restaurant Analytics)
    @GetMapping("/{id}/analytics")
    public ResponseEntity<RestaurantAnalyticsDTO> getAnalytics(@PathVariable Integer id) {

        return ResponseEntity.ok(restaurantService.getRestaurantAnalytics(id));
    }

    //Best-selling MenuItems for a restaurant
    @GetMapping("/{id}/menu/top-sellers")
    public ResponseEntity<List<TopSellingMenuItemDTO>> getTopSellingItems(@PathVariable Integer id) {

        return ResponseEntity.ok(restaurantService.getTopSellingMenuItems(id));
    }

    //menu item search
    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuItemResponseDTO>> searchMenuItems(
            @RequestParam String keyword,
            @RequestParam Integer minCalories,
            @RequestParam Integer maxCalories) {

        return ResponseEntity.ok(restaurantService.searchMenuItems(keyword, minCalories, maxCalories));
    }

    // Search restaurant by name
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponseDTO>> searchByName(@RequestParam String name) {

        return ResponseEntity.ok(restaurantService.searchRestaurantsByName(name));
    }
}
