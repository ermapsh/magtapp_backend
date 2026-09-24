package com.magtapp.pro.gateway.service;

import com.magtapp.pro.gateway.dto.request.PaymentInitRequest;
import com.magtapp.pro.gateway.dto.response.PaymentResponse;
import java.util.UUID;


public interface PaymentService {
    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);
}
