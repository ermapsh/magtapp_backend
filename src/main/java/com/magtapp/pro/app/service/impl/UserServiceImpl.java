package com.magtapp.pro.app.service.impl;

import com.magtapp.pro.app.dto.res.SubscriptionResponse;
import com.magtapp.pro.app.dto.res.UserProfileResponse;
import com.magtapp.pro.app.entity.Subscription;
import com.magtapp.pro.app.entity.User;
import com.magtapp.pro.app.enums.SubscriptionPlan;
import com.magtapp.pro.app.enums.SubscriptionStatus;
import com.magtapp.pro.app.exception.ResourceNotFoundException;
import com.magtapp.pro.app.repository.SubscriptionRepository;
import com.magtapp.pro.app.repository.UserRepository;
import com.magtapp.pro.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public UserProfileResponse getProfile(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + userId
                        )
                );

        Subscription subscription =
                subscriptionRepository.findByUserId(userId).orElse(null);

        if (subscription == null) {
            return new UserProfileResponse(
                    user.getId(),
                    user.getEmail(),
                    new SubscriptionResponse(
                            null,
                            SubscriptionPlan.FREE,
                            SubscriptionStatus.ACTIVE,
                            null,
                            null,
                            false,
                            false
                    )
            );
        }

        boolean isPro =
                subscription.getPlan() != SubscriptionPlan.FREE
                        && subscription.getStatus() == SubscriptionStatus.ACTIVE
                        && subscription.getExpiresAt() != null
                        && subscription.getExpiresAt().isAfter(Instant.now());

        SubscriptionResponse subscriptionResponse =
                new SubscriptionResponse(
                        subscription.getId(),
                        subscription.getPlan(),
                        subscription.getStatus(),
                        subscription.getStartedAt(),
                        subscription.getExpiresAt(),
                        subscription.isAutoRenewing(),
                        isPro
                );

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                subscriptionResponse
        );
    }
}