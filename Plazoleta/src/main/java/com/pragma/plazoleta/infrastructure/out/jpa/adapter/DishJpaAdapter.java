package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.infrastructure.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;


    @Override
    public Dish saveDish(Dish dish) {
        DishEntity dishEntity = dishRepository.save(dishEntityMapper.toEntity(dish));
        return dishEntityMapper.toDishModel(dishEntity);
    }

    @Override
    public Dish getById(Long dishId) {
        return dishEntityMapper.toDishModel(dishRepository.findById(dishId).orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public List<Dish> getAllDishes() {
        List<DishEntity> entityList = dishRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return dishEntityMapper.toDishModelList(entityList);
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }
}