package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.OrderDish;

import java.util.List;


public interface IOrderDishPersistencePort {
    OrderDish createOrderDish(OrderDish orderDish);

    OrderDish getById(Long orderDishId);

    List<OrderDish> getAllOrderDish();

    List<OrderDish> getAllOrderDishByOrder(Long orderId);
}