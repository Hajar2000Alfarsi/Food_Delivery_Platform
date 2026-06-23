package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
    @Query("select a from CustomerAddress a where a.city =:city AND a.isActive = true")
    List<CustomerAddress> findByCity(@Param("city") String city);

    @Query("select a from CustomerAddress a where a.customer.id =:id AND a.isActive = true")
    List<CustomerAddress> findByCustomerId(@Param("id") Integer id);

    @Query("select a from CustomerAddress a where a.id =:id AND a.isActive = true")
    Optional<CustomerAddress> findByActiveId(@Param("id") Integer id);

}
