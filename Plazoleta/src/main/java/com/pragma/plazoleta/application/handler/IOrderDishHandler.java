package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.OrderDishRequestDto;
import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;

public interface IOrderDishHandler {


    OrderDishResponseDto createOrderDish(OrderDishRequestDto orderDishRequestDto, Long orderId);

}