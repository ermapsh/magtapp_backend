package com.magtapp.pro.gateway.repository;

import com.magtapp.pro.gateway.dto.response.CreateOrderResponse;
import com.magtapp.pro.gateway.entity.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByMerchantIdAndReceipt(UUID merchantId, String receipt);
    CreateOrderResponse getById(UUID id, UUID merchantId);
    Optional<Order> findByIdAndMerchantId(UUID id, UUID merchantId);

    /* Doing for more consistency rather than concurrency */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.id= :uuid and o.merchantId= :merchantId")
    Optional<Order> findByIdAndMerchantIdForUpdate(UUID uuid, UUID merchantId);
}