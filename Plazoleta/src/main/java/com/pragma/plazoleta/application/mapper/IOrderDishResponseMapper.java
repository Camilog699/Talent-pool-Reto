package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.OrderDishResponseDto;
import com.pragma.plazoleta.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ICategoryResponseMapper.class, IRestaurantResponseMapper.class})
public interface IOrderDishResponseMapper {

    IDishResponseMapper DISH_RESPONSE_MAPPER = Mappers.getMapper(IDishResponseMapper.class);

    OrderDishResponseDto toResponse(OrderDish orderDishModel);

    List<OrderDishResponseDto> toResponseList(List<OrderDish> orderDishModelList);
}