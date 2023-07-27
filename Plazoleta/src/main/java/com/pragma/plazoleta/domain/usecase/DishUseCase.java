package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;

import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public Dish saveDish(Dish dish) {
        return dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish getById(Long dishId) {
        return dishPersistencePort.getById(dishId);
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishPersistencePort.getAllDishes();
    }

    @Override
    public List<Dish> getDishByRestaurantId(Long id) {
        return dishPersistencePort.getDishByRestaurantId(id);
    }

    @Override
    public void updateDish(Dish dish) {
        dishPersistencePort.updateDish(dish);
    }
}