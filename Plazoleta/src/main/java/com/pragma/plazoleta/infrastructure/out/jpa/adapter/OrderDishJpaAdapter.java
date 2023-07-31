package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.OrderDish;
import com.pragma.plazoleta.domain.spi.IOrderDishPersistencePort;
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IOrderDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public OrderDish createOrderDish(OrderDish orderDish) {
        OrderDishEntity orderDishEntity = orderDishRepository.save(orderDishEntityMapper.toEntity(orderDish));
        return orderDishEntityMapper.toObjectModel(orderDishEntity);
    }

    @Override
    public OrderDish getById(Long orderDishId) {
        return orderDishEntityMapper.toObjectModel(orderDishRepository.findById(orderDishId).orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public List<OrderDish> getAllOrderDish() {
        return orderDishEntityMapper.toOrderDishModelList(orderDishRepository.findAll());
    }

    @Override
    public List<OrderDish> getAllOrderDishByOrder(Long orderId) {
        List<OrderDishEntity> orderDishEntityList = orderDishRepository.findAllByOrderId(orderId);
        return orderDishEntityMapper.toOrderDishModelList(orderDishEntityList);
    }

}