package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {
    @Query("select o from RestaurantOwner o where o.id = :id AND o.isActive = true")
    Optional<RestaurantOwner> getById(@Param("id") Integer id);
}
