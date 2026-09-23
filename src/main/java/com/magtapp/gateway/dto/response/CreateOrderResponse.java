package com.magtapp.gateway.dto.response;

import com.magtapp.common.entity.Money;
import com.magtapp.gateway.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse{
    UUID id;
    UUID merchantId;
    String receipt;
    Money amount;
    OrderStatus status;
    Integer attempts;
    Map<String, Object> notes;
    LocalDateTime expiresAt;
    LocalDateTime createdAt;
}