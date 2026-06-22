package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    @Query("select d from Delivery d where d.driver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> findByDriverIdAndStatus(@Param("driverId") Integer id, @Param("status") String status);

}
