package com.magtapp.pro.gateway.controller;


import com.magtapp.pro.gateway.dto.request.CreateOrderRequest;
import com.magtapp.pro.gateway.dto.response.CreateOrderResponse;
import com.magtapp.pro.common.dto.res.ApiResponse;
import com.magtapp.pro.app.security.UserContext;
import com.magtapp.pro.gateway.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserContext userContext;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        return ApiResponse.created("order created successfully", orderService.createOrder(request));
    }
}
