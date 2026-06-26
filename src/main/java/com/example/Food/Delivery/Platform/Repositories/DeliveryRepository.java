package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    @Query("select d from Delivery d where d.driver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> findByDriverIdAndStatus(@Param("driverId") Integer id, @Param("status") String status);

    @Query("select d from Delivery d where d.driver.id = :driverId AND d.isActive = true")
    List<Delivery> findByActiveDriverId(@Param("driverId") Integer id);

    @Query("select d from Delivery d where d.driver.id = :driverId AND d.status = 'IN_PROGRESS' AND d.isActive = true")
    Optional<Delivery> findInProgressByDriverId(@Param("driverId") Integer id);

    @Query("select d from Delivery d where d.id = :id AND d.isActive = true")
    Optional<Delivery> findByActiveId(@Param("id") Integer id);

    @Query("select d from Delivery d where d.status  = :status  AND d.isActive = true")
    List<Delivery>  findByStatus(@Param("status") String status);

    @Query("select d.driver, count(d) from Delivery d where d.status = 'DELIVERED' AND d.isActive = true group by d.driver order by count(d) desc")
    List<Object[]> getDriverLeaderboard();
}
