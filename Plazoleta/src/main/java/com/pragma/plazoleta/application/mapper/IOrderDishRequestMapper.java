package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.OrderDishRequestDto;
import com.pragma.plazoleta.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishRequestMapper {

    @Mapping(source = "orderDishRequest.dishId", target = "dishId.id")
    OrderDish toOrderDish(OrderDishRequestDto orderDishRequest);
}
