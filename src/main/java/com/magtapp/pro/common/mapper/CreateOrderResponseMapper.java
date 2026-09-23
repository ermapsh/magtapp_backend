package com.magtapp.pro.common.mapper;

import com.magtapp.pro.gateway.dto.response.CreateOrderResponse;
import com.magtapp.pro.app.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateOrderResponseMapper {

    CreateOrderResponse toResponse(Order order);
}
