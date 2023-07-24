package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IDishPersistencePort {
    Dish saveDish(Dish dish);


    Dish getById(Long dishId);

    List<Dish> getAllDishes();

    void updateDish(Dish dish);
}