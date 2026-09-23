package com.magtapp.pro.app.repository;

import com.magtapp.pro.app.entity.Subscription;
import com.magtapp.pro.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByUserId(UUID userId);

    Optional<Subscription> findByUser(User user);
}