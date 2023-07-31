package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Order createOrder(Order order) {
        return orderPersistencePort.createOrder(order);
    }

    @Override
    public Order getById(Long orderId) {
        return orderPersistencePort.getById(orderId);
    }

    @Override
    public List<Order> getAllOrdersByOrderState(OrderState orderState, Long restaurantId) {
        return orderPersistencePort.getAllOrdersByOrderState(orderState, restaurantId);
    }

    @Override
    public void updateOrder(Order order, Long idOrder) {
        orderPersistencePort.updateOrder(order, idOrder);
    }

}