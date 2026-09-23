package com.magtapp.pro.repository;

import com.magtapp.pro.entity.Subscription;
import com.magtapp.pro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByUserId(UUID userId);

    Optional<Subscription> findByUser(User user);
}