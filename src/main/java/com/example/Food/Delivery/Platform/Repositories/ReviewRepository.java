package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("select r from Review r where r.restaurant.id = :restaurantId AND r.isActive = true")
    List<Review> findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("select r from Review r where r.driver.id = :driverId AND r.isActive = true")
    List<Review> findByDriverId(@Param("driverId") Integer driverId);

    @Query("select r from Review r where r.id = :id AND r.isActive = true")
    Optional<Review> findByActiveId(@Param("id") Integer id);

    @Query("select avg(r.rating) from Review r where r.restaurant.id = :restaurantId AND r.isActive = true")
    Double getAverageRating(@Param("restaurantId") Integer restaurantId);

    @Query("select avg(r.rating) from Review r where r.restaurant.id = :restaurantId AND r.isActive = true")
    Double getRestaurantAverage(@Param("restaurantId") Integer restaurantId);

    @Query("select avg(r.rating) from Review r where r.driver.id = :driverId AND r.isActive = true")
    Double getDriverAverage(@Param("driverId") Integer driverId);

    @Query("select r from Review r where r.restaurant.id = :restaurantId AND r.isActive = true")
    Page<Review> getRestaurantReviews(@Param("restaurantId") Integer restaurantId, Pageable pageable);
}
