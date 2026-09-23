package com.magtapp.pro.service;

import com.magtapp.pro.dto.res.SubscriptionResponse;

public interface SubscriptionService {

    SubscriptionResponse subscribe();

    SubscriptionResponse getSubscription();
}