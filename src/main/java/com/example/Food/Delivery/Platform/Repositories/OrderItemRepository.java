package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query("select oi.menuItem.id, oi.menuItem.name,sum(oi.quantity) from OrderItem oi " +
            "where oi.menuItem.restaurant.id = :restaurantId AND oi.isActive = true " +
            "group by oi.menuItem.id, oi.menuItem.name order by sum(oi.quantity) desc")
    List<Object[]> getTopSellingItems(@Param("restaurantId") Integer restaurantId);

}
