package com.magtapp.pro.gateway.controller;


import com.magtapp.pro.gateway.dto.request.PaymentInitRequest;
import com.magtapp.pro.gateway.dto.response.PaymentResponse;
import com.magtapp.pro.gateway.service.PaymentService;
import com.magtapp.pro.common.dto.res.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Value("${payment.gateway.merchantId}")
    private UUID merchantId; // this is merchantId that allow by bank or any other service provider

    @PostMapping("")
    public ResponseEntity<ApiResponse<PaymentResponse>> initiate(@Valid @RequestBody PaymentInitRequest request) {
        return ApiResponse.created(
                "Payment initiated",
                paymentService.initiate(merchantId, request)
        );
    }

    @PostMapping("/{paymentId}/capture")
    public ResponseEntity<ApiResponse<PaymentResponse>> capture(@RequestParam UUID paymentId) {
        return ApiResponse.ok(
                "Payment capture",
                paymentService.capture(merchantId, paymentId)
        );
    }

}
