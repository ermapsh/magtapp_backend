package com.magtapp.pro.gateway.service.impl;

import com.magtapp.pro.app.dto.req.SubscribeRequest;
import com.magtapp.pro.app.enums.SubscriptionPlan;
import com.magtapp.pro.app.security.UserContext;
import com.magtapp.pro.app.service.SubscriptionService;
import com.magtapp.pro.common.mapper.PaymentMapper;
import com.magtapp.pro.gateway.dto.request.PaymentRequest;
import com.magtapp.pro.gateway.dto.response.PaymentResult;
import com.magtapp.pro.gateway.entity.Order;
import com.magtapp.pro.app.exception.ResourceNotFoundException;
import com.magtapp.pro.gateway.dto.request.PaymentInitRequest;
import com.magtapp.pro.gateway.dto.response.PaymentResponse;
import com.magtapp.pro.gateway.entity.Payment;
import com.magtapp.pro.gateway.enums.OrderStatus;
import com.magtapp.pro.gateway.enums.PaymentStatus;
import com.magtapp.pro.gateway.processor.PaymentProcessor;
import com.magtapp.pro.gateway.repository.PaymentRepository;
import com.magtapp.pro.gateway.service.PaymentService;
import com.magtapp.pro.gateway.statemachine.PaymentTransitionService;
import com.magtapp.pro.gateway.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransitionService paymentTransitionService;
    private final PaymentProcessor paymentProcessor;
    private final PaymentMapper paymentMapper;
    private final SubscriptionService subscriptionService;
    private final UserContext userContext;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PaymentResponse initiate(
            UUID merchantId,
            PaymentInitRequest request
    ) {

        Order order = orderRepository
                .findByIdAndUserIdForUpdate(
                        request.orderId(),
                        userContext.getUserId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order is Invalid")
                );

        if (order.getStatus() != OrderStatus.CREATED &&
                order.getStatus() != OrderStatus.ATTEMPTED) {

            throw new ResourceNotFoundException(
                    "Order cannot accept payment in status: "
                            + order.getStatus()
            );
        }

        order.setStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts() + 1);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .money(order.getAmount())
                .idempotency(UUID.randomUUID().toString())
                .paymentMethod(request.method())
                .methodDetails(request.methodDetails())
                .paymentStatus(PaymentStatus.CREATED)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        PaymentRequest paymentRequest = new PaymentRequest(
                savedPayment.getId(),
                order.getId(),
                merchantId,
                savedPayment.getMoney(),
                savedPayment.getPaymentMethod(),
                savedPayment.getMethodDetails()
        );

        PaymentResult result =
                paymentProcessor.authorize(paymentRequest);

        switch (result) {

            case PaymentResult.Success success -> {
                payment.setProcessorReference(
                        success.bankReference()
                );

                payment.setPaymentStatus(
                        PaymentStatus.CAPTURED // there is large compliance we have to follow so that's why i capture here payment as completed
                );

                order.setStatus(OrderStatus.PAID);

                SubscribeRequest sub =
                        new SubscribeRequest(order.getSubscriptionPlan());

                subscriptionService.subscribe(
                        order.getUser().getId(),
                        sub
                );
            }

            case PaymentResult.Failure failure -> {
                payment.setPaymentStatus(
                        PaymentStatus.FAILED
                );

                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(
                        failure.errorDescription()
                );
            }

            case PaymentResult.Pending pending -> {
                payment.setProcessorReference(
                        pending.registrationRef()
                );
            }
        }

        orderRepository.save(order);
        paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }
}
























