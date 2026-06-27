package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<FoodOrder, Integer> {
    @Query("select o from FoodOrder o where o.customer.id = :customerId AND o.isActive = true")
    List<FoodOrder> findByCustomerId(@Param("customerId") Integer id);

    @Query("select o from FoodOrder o where o.restaurant.id = :restaurantId AND o.status = :status AND o.isActive = true")
    List<FoodOrder> findByRestaurantIdAndStatus(@Param("restaurantId") Integer id, @Param("status") String status);

    @Query("select o from FoodOrder o where o.orderDate BETWEEN :start AND :end AND o.isActive = true")
    List<FoodOrder> findByOrderDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select COUNT(o) FROM FoodOrder o where o.restaurant.id = :restaurantId AND o.status = 'COMPLETED' AND o.isActive = true")
    Long countCompletedOrders(@Param("restaurantId") Integer id);

    @Query("select COALESCE(SUM(o.totalAmount),0) from FoodOrder o where DATE(o.orderDate) = :date AND o.status = 'DELIVERED' AND o.isActive = true")
    Double sumDeliveredRevenueByDate(@Param("date") LocalDateTime date);

    @Query("select o from FoodOrder o where o.id = :id AND o.isActive = true")
    Optional<FoodOrder> findByActiveId(@Param("id") Integer id);

    //find customer order with status and order date
    @Query("select o from FoodOrder o where o.customer.id = :customerId AND o.status = :status AND o.orderDate between :from and :to AND o.isActive = true")
    Page<FoodOrder> findCustomerOrders(
            @Param("customerId") Integer customerId,
            @Param("status") String status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

}
