package com.magtapp.pro.gateway.repository;

import com.magtapp.pro.gateway.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}