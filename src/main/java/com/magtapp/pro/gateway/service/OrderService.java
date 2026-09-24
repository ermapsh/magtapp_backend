package com.magtapp.pro.gateway.service;

import com.magtapp.pro.gateway.dto.request.CreateOrderRequest;
import com.magtapp.pro.gateway.dto.response.CreateOrderResponse;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest receipt);
}
