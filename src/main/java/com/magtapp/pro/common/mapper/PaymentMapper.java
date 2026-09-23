package com.magtapp.pro.common.mapper;

import com.magtapp.pro.gateway.dto.response.PaymentResponse;
import com.magtapp.pro.gateway.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment);

    @Mapping(target = "orderId", source = "order.id")
    List<PaymentResponse>  toResponseList(List<Payment> paymentsList);

}
