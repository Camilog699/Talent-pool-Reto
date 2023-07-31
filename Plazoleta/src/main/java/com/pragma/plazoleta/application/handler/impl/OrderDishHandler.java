package com.pragma.plazoleta.application.handler.impl;
import com.pragma.plazoleta.application.dto.request.OrderDishRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishResponseDto;
import com.pragma.plazoleta.application.handler.IOrderDishHandler;
import com.pragma.plazoleta.application.mapper.IOrderDishRequestMapper;
import com.pragma.plazoleta.application.mapper.IOrderDishResponseMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IOrderDishServicePort;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderDishHandler implements IOrderDishHandler {

    private final IOrderDishServicePort orderDishServicePort;
    private final IOrderDishRequestMapper orderDishRequestMapper;
    private final IOrderDishResponseMapper orderDishResponseMapper;
    private final IDishServicePort dishServicePort;
    private final IOrderServicePort orderServicePort;

    @Override
    public OrderDishResponseDto createOrderDish(OrderDishRequestDto orderDishRequestDto, Long orderId) {
        Dish dishModel = dishServicePort.getById(orderDishRequestDto.getDishId());
        Order orderModel = orderServicePort.getById(orderId);

        OrderDish orderDishModel = orderDishRequestMapper.toOrderDish(orderDishRequestDto);
        orderDishModel.setDishId(dishModel);
        orderDishModel.setOrderId(orderModel);

        orderDishServicePort.createOrderDish(orderDishModel);
        return orderDishResponseMapper.toResponse(orderDishModel);
    }

}