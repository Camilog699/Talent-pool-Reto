package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IDishPersistencePort {
    Dish saveDish(Dish dish);


    Dish getById(Long dishId);

    List<Dish> getAllDishes();

    List<Dish> getDishByRestaurantId(int page, int size, Long id);

    void updateDish(Dish dish);
}