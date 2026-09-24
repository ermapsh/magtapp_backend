package com.magtapp.pro.app.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.magtapp.pro.app.enums.SubscriptionPlan;
import com.magtapp.pro.app.enums.SubscriptionStatus;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubscriptionResponse(
        UUID id,
        SubscriptionPlan plan,
        SubscriptionStatus status,
        Instant startedAt,
        Instant expiresAt,
        boolean autoRenewing,
        boolean isPro
) {
}