package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IDishServicePort {

    Dish saveDish(Dish dish);

    Dish getById(Long dishId);

    List<Dish> getAllDishes();

    List<Dish> getDishByRestaurantId(int page, int size, Long restaurantId);

    void updateDish(Dish dish);
}