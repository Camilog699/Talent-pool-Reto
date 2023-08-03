package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderStateResponseDto;
import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.domain.model.Order;

import java.util.List;

public interface IOrderHandler {


    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderStateResponseDto> getAllOrdersByOrderState(OrderState orderState);

    OrderResponseDto updateOrder(Order order, Long orderId);

    OrderResponseDto assignOrderToEmployee(Long orderId);

    OrderResponseDto orderReady(Long orderId);

    OrderResponseDto orderDelivered(Long orderId, Long pin);

    //OrderResponseDto orderCancel(Long orderId);

}