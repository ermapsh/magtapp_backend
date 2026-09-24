package com.magtapp.pro.gateway.processor;

import com.magtapp.pro.gateway.dto.request.PaymentRequest;
import com.magtapp.pro.gateway.dto.response.PaymentResult;

public interface PaymentProcessor {
    PaymentResult authorize(PaymentRequest request);
}