package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = orderRepository.save(orderEntityMapper.toEntity(order));
        return orderEntityMapper.toObjectModel(orderEntity);
    }

    @Override
    public Order getById(Long orderId) {
        return orderEntityMapper.toObjectModel(orderRepository.findById(orderId).orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public List<Order> getAllOrdersByOrderState(OrderState orderState, Long restaurantId) {
        return orderEntityMapper.toObjectModelList(orderRepository.findAllByOrderState(orderState, restaurantId));
    }

    @Override
    public void updateOrder(Order order, Long idOrder) {
        orderRepository.save(orderEntityMapper.toEntity(order));
    }

}