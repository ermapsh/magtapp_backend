package com.magtapp.pro.app.service;

import com.magtapp.pro.app.dto.req.SubscribeRequest;
import com.magtapp.pro.app.dto.res.SubscriptionResponse;

import java.util.UUID;

public interface SubscriptionService {
    SubscriptionResponse subscribe(UUID userId, SubscribeRequest request);
}