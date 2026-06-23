package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<DeliveryDriver, Integer> {
    @Query("select d from DeliveryDriver d where d.isActive = true")
    List<DeliveryDriver> findAllActive();

    @Query("select d from DeliveryDriver d where d.id = :id AND d.isActive = true")
    Optional<DeliveryDriver> findByActiveID(@Param("id") Integer id);

    @Query("select d from DeliveryDriver d where d.isOnline = true AND d.isActive = true")
    List<DeliveryDriver> findByIsOnlineTrue();

}
