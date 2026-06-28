package com.example.Food.Delivery.Platform.Repositories;

import com.example.Food.Delivery.Platform.Entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("select p from Payment p where p.id = :id and p.isActive = true")
    Optional<Payment> findByActiveId(@Param("id") Integer id);

    @Query("select p from Payment p where p.order.id = :orderId and p.isActive = true")
    Optional<Payment> findByOrderId(@Param("orderId") Integer orderId);

    @Query("""
SELECT p FROM Payment p
WHERE (:method IS NULL OR p.paymentMethod = :method)
AND (:status IS NULL OR p.status = :status)
AND (:from IS NULL OR p.createdDate >= :from)
AND (:to IS NULL OR p.createdDate <= :to)
AND p.isActive = true
""")
    Page<Payment> filterPayments(
            @Param("method") String method,
            @Param("status") String status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable
    );

    @Query("SELECT p.paymentMethod, SUM(p.amount) FROM Payment p WHERE p.isActive = true GROUP BY p.paymentMethod")
    List<Object[]> getTotalByMethod();
}
