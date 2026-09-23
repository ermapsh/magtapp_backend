package com.magtapp.gateway.service;

import com.magtapp.gateway.dto.request.PaymentInitRequest;
import com.magtapp.gateway.dto.response.PaymentResponse;
import java.util.UUID;


public interface PaymentService {
    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);
    PaymentResponse capture(UUID merchantId, UUID paymentId);
    void resolveAuthorization(UUID paymentId, Boolean approve, String bankRef, String simBankErrorCode, String simulatedBankDecline);
}
