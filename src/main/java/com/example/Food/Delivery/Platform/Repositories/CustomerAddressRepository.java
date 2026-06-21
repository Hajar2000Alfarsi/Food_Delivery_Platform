package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
    @Query("select a from CustomerAddress a where a.city =:city AND a.isActive = true")
    List<CustomerAddress> findByCity(@Param("city") String city);

}
