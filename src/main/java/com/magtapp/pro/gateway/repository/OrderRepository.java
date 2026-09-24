package com.magtapp.pro.gateway.repository;

import com.magtapp.pro.gateway.entity.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    boolean existsByUserIdAndReceipt(UUID userId, String receipt);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT o
            FROM Order o
            WHERE o.id = :orderId
              AND o.user.id = :userId
            """)
    Optional<Order> findByIdAndUserIdForUpdate(
            @Param("orderId") UUID orderId,
            @Param("userId") UUID userId
    );
}