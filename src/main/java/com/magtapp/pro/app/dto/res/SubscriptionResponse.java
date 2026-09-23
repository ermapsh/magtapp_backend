package com.magtapp.pro.app.dto.res;

import com.magtapp.pro.app.enums.SubscriptionPlatform;
import com.magtapp.pro.app.enums.SubscriptionStatus;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionResponse (
        UUID id,
        SubscriptionStatus status,
        SubscriptionPlatform platform,
        String productId,
        Instant startedAt,
        Instant expiresAt,
        boolean autoRenewing,
        boolean isPro
){
}
