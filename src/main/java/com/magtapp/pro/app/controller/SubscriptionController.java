package com.magtapp.pro.app.controller;

import com.magtapp.pro.common.dto.res.ApiResponse;
import com.magtapp.pro.app.dto.res.SubscriptionResponse;
import com.magtapp.pro.app.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> subscribe() {

        SubscriptionResponse response =
                subscriptionService.subscribe();

        return ApiResponse.created(
                "Subscription activated successfully",
                response
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<SubscriptionResponse>> getSubscription() {

        SubscriptionResponse response =
                subscriptionService.getSubscription();

        return ApiResponse.ok(
                "Subscription fetched successfully",
                response
        );
    }
}