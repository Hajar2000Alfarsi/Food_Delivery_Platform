package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.DTO.response.RestaurantResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.cuisineType) = LOWER(:cuisineType) AND r.isActive = true")
    List<Restaurant> findByCuisineTypeIgnoreCase(@Param("cuisineType") String cuisineType);

    @Query("select r from Restaurant r where r.acceptingOrders = true AND r.isActive = true")
    List<Restaurant> findByAcceptingOrdersTrue();

    @Query("select r from Restaurant r WHERE r.deliveryFee <= :fee AND r.isActive = true")
    List<Restaurant> findByDeliveryFeeLessThanEqual(@Param("fee") double deliveryFee);

    @Query("select r from Restaurant r where r.restaurantOwner.id = :ownerId AND r.isActive = true")
    List<Restaurant> findByOwnerId(@Param("ownerId") Integer id);

    @Query("select r from Restaurant r where lower(r.name) LIKE lower(CONCAT('%', :keyword, '%')) AND r.isActive = true")
    List<Restaurant> searchByName(@Param("keyword") String keyword);

    @Query("select r from Restaurant r where r.id = :id AND r.isActive = true")
    Optional<Restaurant> findByActiveId(@Param("id") Integer id);

    @Query("select r from Restaurant r where r.isActive = true")
    List<Restaurant> findAllActive();

    @Query("select r from Restaurant r where r.id = :id AND r.isActive = true")
    Optional<RestaurantResponseDTO> findByActiveIdDTO(@Param("id") Integer id);

}
