package com.magtapp.pro.gateway.dto.request;


import com.magtapp.pro.common.entity.Money;
import com.magtapp.pro.gateway.enums.PaymentMethod;

import java.util.Map;
import java.util.UUID;

public record PaymentRequest(
        UUID paymentId,
        UUID orderId,
        UUID merchantId,
        Money amount,
        PaymentMethod method,
        Map<String, Object> methodDetails
) {
}
