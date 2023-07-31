package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.*;
import com.pragma.plazoleta.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ICategoryResponseMapper.class, IRestaurantResponseMapper.class})
public interface IOrderResponseMapper {

    IRestaurantResponseMapper RESTAURANT_RESPONSE_MAPPER_INSTANCE = Mappers.getMapper(IRestaurantResponseMapper.class);
    IOrderDishResponseMapper ORDER_DISH_RESPONSE_MAPPER = Mappers.getMapper(IOrderDishResponseMapper.class);


    OrderResponseDto toResponse(Order order, List<OrderDishResponseDto> orderDishResponseDtoList);

    default List<OrderStateResponseDto> toResponseList(List<Order> orderModelList, List<Restaurant> restaurantModelList, List<OrderDish> orderDishModelList) {
        return orderModelList.stream()
                .map(order -> {
                    OrderStateResponseDto orderResponseDto = new OrderStateResponseDto();

                    orderResponseDto.setOrderId(order.getId());
                    orderResponseDto.setDate(order.getDate());
                    orderResponseDto.setOrderState(order.getStatus());

                    orderResponseDto.setRestaurantId(RESTAURANT_RESPONSE_MAPPER_INSTANCE.toResponse(restaurantModelList.stream().filter(
                            restaurant -> restaurant.getId().equals(order.getRestaurantId().getId())
                    ).findFirst().get()));

                    List<OrderDish> orderDishModelListFiltered = orderDishModelList.stream().filter(
                            orderDishModel -> orderDishModel.getOrderId().getId().equals(order.getId())
                    ).collect(Collectors.toList());

                    List<Long> longList = orderDishModelListFiltered.stream().map(
                            OrderDish::getId
                    ).collect(Collectors.toList());

                    orderResponseDto.setOrderDishIds(longList);

                    return orderResponseDto;
                }).collect(Collectors.toList());
    }

}
