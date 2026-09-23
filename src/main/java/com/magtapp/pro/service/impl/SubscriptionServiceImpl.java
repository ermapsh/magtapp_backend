package com.magtapp.pro.service.impl;

import com.magtapp.pro.dto.res.SubscriptionResponse;
import com.magtapp.pro.entity.Subscription;
import com.magtapp.pro.entity.User;
import com.magtapp.pro.enums.SubscriptionPlatform;
import com.magtapp.pro.enums.SubscriptionStatus;
import com.magtapp.pro.repository.SubscriptionRepository;
import com.magtapp.pro.repository.UserRepository;
import com.magtapp.pro.security.UserContext;
import com.magtapp.pro.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserContext userContext;

    @Override
    @Transactional
    public SubscriptionResponse subscribe() {

        UUID userId = userContext.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Instant now = Instant.now();

        Subscription subscription = subscriptionRepository
                .findByUserId(userId)
                .orElseGet(() -> Subscription.builder()
                        .user(user)
                        .build());

        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setPlatform(SubscriptionPlatform.ANDROID);
        subscription.setProductId("magtapp_pro_monthly");
        subscription.setProviderSubscriptionId("MOCK-" + UUID.randomUUID());
        subscription.setStartedAt(now);
        subscription.setExpiresAt(now.plus(30, ChronoUnit.DAYS));
        subscription.setAutoRenewing(true);
        subscription.setCancelledAt(null);

        Subscription saved = subscriptionRepository.save(subscription);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponse getSubscription() {

        UUID userId = userContext.getUserId();

        Subscription subscription = subscriptionRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Subscription not found"
                ));

        return toResponse(subscription);
    }

    private SubscriptionResponse toResponse(Subscription subscription) {

        boolean isPro =
                subscription.getStatus() == SubscriptionStatus.ACTIVE
                        && subscription.getExpiresAt() != null
                        && subscription.getExpiresAt().isAfter(Instant.now());

        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getStatus(),
                subscription.getPlatform(),
                subscription.getProductId(),
                subscription.getStartedAt(),
                subscription.getExpiresAt(),
                subscription.isAutoRenewing(),
                isPro
        );
    }
}