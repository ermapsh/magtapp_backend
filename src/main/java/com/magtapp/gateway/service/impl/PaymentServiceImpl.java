package com.magtapp.gateway.service.impl;

import com.magtapp.gateway.dto.request.PaymentInitRequest;
import com.magtapp.gateway.dto.response.PaymentResponse;
import com.magtapp.gateway.repository.PaymentRepository;
import com.magtapp.gateway.service.PaymentService;
import com.magtapp.gateway.statemachine.PaymentTransitionService;
import com.magtapp.pro.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransitionService paymentTransitionService;

    @Override
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequest request) {
        return null;
    }

    @Override
    public PaymentResponse capture(UUID merchantId, UUID paymentId) {
        return null;
    }

    @Override
    public void resolveAuthorization(UUID paymentId, Boolean approve, String bankRef, String simBankErrorCode, String simulatedBankDecline) {

    }
}
























