package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.OrderDish;

import java.util.List;


public interface IOrderDishServicePort {

    OrderDish createOrderDish(OrderDish orderDish);

    OrderDish getById(Long orderDishId);

    List<OrderDish> getAllOrderDish();

    List<OrderDish> getAllOrderDishByOrder(Long orderId);
}