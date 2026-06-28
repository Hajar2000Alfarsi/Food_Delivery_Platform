package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Query("select m from MenuItem m where m.id = :id AND m.isActive = true")
    Optional<MenuItem> findByActiveId(@Param("id") Integer id);

    @Query("select m from MenuItem m where m.restaurant.id = :id AND m.isActive = true")
    List<MenuItem> findByRestaurantId(@Param("id") Integer id);

    @Query("select m from MenuItem m where m.restaurant.id = :id AND m.isAvailable = true AND m.isActive = true")
    List<MenuItem> findAvailableByRestaurantId(@Param("id") Integer id);

    @Query("select m from MenuItem m where m.isVegetarian = true AND m.isActive = true")
    List<MenuItem> findVegetarianItems();

    @Query("select m from MenuItem m where m.price BETWEEN :min AND :max AND m.isActive = true")
    List<MenuItem> findByPriceBetween(@Param("min") Double min, @Param("max") Double max);

    @Query("select m from MenuItem m where " +
            "lower(m.name) like lower(concat('%', :keyword, '%')) AND " +
            "m.calories between :minCalories AND :maxCalories AND m.isActive = true")
    List<MenuItem> searchMenuItems(
            @Param("keyword") String keyword,
            @Param("minCalories") Integer minCalories,
            @Param("maxCalories") Integer maxCalories);

}
