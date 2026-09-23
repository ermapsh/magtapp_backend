package com.magtapp.pro.service;

import com.magtapp.gateway.dto.request.CreateOrderRequest;
import com.magtapp.gateway.dto.response.CreateOrderResponse;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest receipt);
}
