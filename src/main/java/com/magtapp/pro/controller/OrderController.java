package com.magtapp.pro.controller;


import com.magtapp.gateway.dto.request.CreateOrderRequest;
import com.magtapp.gateway.dto.response.CreateOrderResponse;
import com.magtapp.common.dto.res.ApiResponse;
import com.magtapp.pro.security.UserContext;
import com.magtapp.pro.service.OrderService;
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
