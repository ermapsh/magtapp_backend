package com.magtapp.common.mapper;

import com.magtapp.gateway.dto.response.CreateOrderResponse;
import com.magtapp.pro.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateOrderResponseMapper {

    CreateOrderResponse toResponse(Order order);
}
