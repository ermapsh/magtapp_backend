package com.magtapp.pro.app.service.impl;


import com.magtapp.pro.common.mapper.CreateOrderResponseMapper;
import com.magtapp.pro.gateway.dto.request.CreateOrderRequest;
import com.magtapp.pro.gateway.dto.response.CreateOrderResponse;
import com.magtapp.pro.app.entity.Order;
import com.magtapp.pro.app.exception.DuplicateResourceException;
import com.magtapp.pro.app.repository.OrderRepository;
import com.magtapp.pro.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Value("${payment.order.default_order_expiry_minutes}")
    private int defaultOrderExpiryMinutes;

    @Value("${payment.gateway.merchantId}")
    private UUID merchantId; // this is merchantId that allow by bank or any other service provide

    private final OrderRepository orderRepository;
    private final CreateOrderResponseMapper createOrderResponseMapper;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        if (request.receipt() != null &&
                orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())) {
            throw new DuplicateResourceException("Order with receipt already exists" + request.receipt());
        }

        Order newOrder = Order.builder().
                receipt(request.receipt()).
                amount(request.amount()).
                notes(request.notes()).
                expiresAt(request.expiresAt() != null ? request.expiresAt() : LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes)).
                merchantId(merchantId).
                build();

        Order savedOrder = orderRepository.save(newOrder);

        return createOrderResponseMapper.toResponse(savedOrder);
    }
}
