package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {
    @Mapping(source = "orderRequestDto.restaurantId", target = "restaurantId.id")
    Order toOrder(OrderRequestDto orderRequestDto);
}
