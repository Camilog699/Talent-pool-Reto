package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.domain.model.Order;

import java.util.List;


public interface IOrderServicePort {

    Order createOrder(Order order);

    Order getById(Long orderId);

    List<Order> getAllOrdersByOrderState(OrderState orderState, Long restaurantId);

    void updateOrder(Order order, Long orderId);
}