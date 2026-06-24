package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {
    @Query("select o from RestaurantOwner o where o.id = :id AND o.isActive = true")
    Optional<RestaurantOwner> findByActiveId(@Param("id") Integer id);

    @Query("SELECT o FROM RestaurantOwner o WHERE o.email = :email AND o.isActive = true ")
    Optional<RestaurantOwner> getByEmail(String email);

    @Query("SELECT o FROM RestaurantOwner o where o.isActive = true")
    List<RestaurantOwner> getAllOwners();

    @Query("SELECT o FROM RestaurantOwner o WHERE o.id = :id AND o.isActive = true")
    Optional<RestaurantOwner> getOwnerById(Integer id);

    @Query("SELECT o FROM RestaurantOwner o WHERE o.businessLicenseCode = :license AND o.isActive = true")
    Optional<RestaurantOwner> getByLicense(String license);
}
