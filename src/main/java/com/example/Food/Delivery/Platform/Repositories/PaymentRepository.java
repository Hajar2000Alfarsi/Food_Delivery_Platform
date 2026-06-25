package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("select p from Payment p where p.id = :id and p.isActive = true")
    Optional<Payment> findByActiveId(@Param("id") Integer id);

    @Query("select p from Payment p where p.order.id = :orderId and p.isActive = true")
    Optional<Payment> findByOrderId(@Param("orderId") Integer orderId);
}
