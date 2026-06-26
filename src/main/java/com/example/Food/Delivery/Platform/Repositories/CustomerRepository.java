package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("select c from Customer c where c.email=:email AND c.isActive=true")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("select c from Customer c where c.loyaltyPoints >= :points AND c.isActive = true")
    List<Customer> findByLoyaltyPointsGreaterThanEqual(@Param("points") Integer loyaltyPoints);

    @Query("select c from Customer c where c.createdDate BETWEEN :start AND :end AND c.isActive = true")
    List<Customer> findCustomersByDateRange(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);

    @Query("select c from Customer c where c.id = :id AND c.isActive = true")
    Optional<Customer> findByActiveId(@Param("id") Integer id);

    @Query("select c from Customer c where c.isActive = true")
    List<Customer> findAllActive();

    @Query("select c from Customer c where c.isActive = true order by c.loyaltyPoints desc")
    List<Customer> findTopCustomersByLoyalty(org.springframework.data.domain.Pageable pageable);
}

