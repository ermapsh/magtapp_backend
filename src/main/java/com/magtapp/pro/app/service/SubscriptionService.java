package com.magtapp.pro.app.service;

import com.magtapp.pro.app.dto.res.SubscriptionResponse;

public interface SubscriptionService {

    SubscriptionResponse subscribe();

    SubscriptionResponse getSubscription();
}