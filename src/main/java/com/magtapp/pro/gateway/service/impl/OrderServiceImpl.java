package com.magtapp.pro.gateway.service.impl;

import com.magtapp.pro.app.entity.User;
import com.magtapp.pro.app.exception.DuplicateResourceException;
import com.magtapp.pro.app.exception.ResourceNotFoundException;
import com.magtapp.pro.app.security.UserContext;
import com.magtapp.pro.app.repository.UserRepository;
import com.magtapp.pro.common.mapper.CreateOrderResponseMapper;
import com.magtapp.pro.gateway.dto.request.CreateOrderRequest;
import com.magtapp.pro.gateway.dto.response.CreateOrderResponse;
import com.magtapp.pro.gateway.entity.Order;
import com.magtapp.pro.gateway.repository.OrderRepository;
import com.magtapp.pro.gateway.service.OrderService;
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

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CreateOrderResponseMapper createOrderResponseMapper;
    private final UserContext userContext;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        UUID userId = userContext.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + userId
                        )
                );

        if (request.receipt() != null &&
                orderRepository.existsByUserIdAndReceipt(
                        userId,
                        request.receipt()
                )) {

            throw new DuplicateResourceException(
                    "Order with receipt already exists: "
                            + request.receipt()
            );
        }

        Order newOrder = Order.builder()
                .user(user)
                .amount(request.amount())
                .subscriptionPlan(request.subscriptionPlan())
                .receipt(request.receipt())
                .notes(request.notes())
                .expiresAt(
                        request.expiresAt() != null
                                ? request.expiresAt()
                                : LocalDateTime.now()
                                .plusMinutes(defaultOrderExpiryMinutes)
                )
                .build();

        Order savedOrder = orderRepository.save(newOrder);

        return createOrderResponseMapper.toResponse(savedOrder);
    }
}