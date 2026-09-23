package com.magtapp.pro.gateway.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.magtapp.pro.common.entity.Money;
import com.magtapp.pro.gateway.enums.PaymentMethod;
import com.magtapp.pro.gateway.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentResponse(
        UUID id,
        UUID orderId,
        UUID merchantId,
        String idempotency,
        String bankReference,
        Money money,
        PaymentMethod paymentMethod,
        Map<String, Object> methodDetails,
        PaymentStatus paymentStatus,
        String errorCode,
        String errorDescription,
        LocalDateTime authorizedAt,
        LocalDateTime capturedAt,
        LocalDateTime failedAt,
        LocalDateTime refundedAt
) {
}