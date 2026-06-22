package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository {
    @Query("select o from FoodOrder o where o.customer.id = :customerId AND o.isActive = true")
    List<FoodOrder> findByCustomerId(@Param("customerId") Integer id);

    @Query("select o from FoodOrder o where o.restaurant.id = restaurantId AND o.status = :status AND o.isActive = true")
    List<FoodOrder> findByRestaurantIdAndStatus(@Param("restaurantId") Integer id, @Param("status") String status);

    @Query("select o from FoodOrder o where o.orderDate BETWEEN :start AND :end AND o.isActive = true")
    List<FoodOrder> findByOrderDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select COUNT(o) FROM FoodOrder where o.restaurant.id = restaurantId AND o.status = 'COMPLETED' AND o.isActive = true")
    Long countCompletedOrders(@Param("restaurantId") Integer id);

}
