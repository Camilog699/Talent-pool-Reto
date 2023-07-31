package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IOrderDishServicePort;
import com.pragma.plazoleta.domain.model.OrderDish;
import com.pragma.plazoleta.domain.spi.IOrderDishPersistencePort;

import java.util.List;

public class OrderDishUseCase implements IOrderDishServicePort {

    private final IOrderDishPersistencePort orderDishPersistencePort;

    public OrderDishUseCase(IOrderDishPersistencePort orderDishPersistencePort) {
        this.orderDishPersistencePort = orderDishPersistencePort;
    }

    @Override
    public OrderDish createOrderDish(OrderDish orderDish) {
        return orderDishPersistencePort.createOrderDish(orderDish);
    }

    @Override
    public OrderDish getById(Long orderDishId) {
        return orderDishPersistencePort.getById(orderDishId);
    }

    @Override
    public List<OrderDish> getAllOrderDish() {
        return orderDishPersistencePort.getAllOrderDish();
    }

    @Override
    public List<OrderDish> getAllOrderDishByOrder(Long orderId) {
        return orderDishPersistencePort.getAllOrderDishByOrder(orderId);
    }

}