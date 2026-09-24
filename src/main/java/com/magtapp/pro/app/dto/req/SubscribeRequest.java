package com.magtapp.pro.app.dto.req;

import com.magtapp.pro.app.enums.SubscriptionPlan;
import jakarta.validation.constraints.NotNull;

public record SubscribeRequest(
    @NotNull
    SubscriptionPlan plan
){}
