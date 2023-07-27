package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;

import java.util.List;
import java.util.Optional;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantPersistencePort.saveRestaurant(restaurant);
    }


    @Override
    public Restaurant getById(Long userId) {
        return restaurantPersistencePort.getById(userId);
    }

    @Override
    public List<Restaurant> getAllRestaurants(int pageN, int size) {
        return restaurantPersistencePort.getAllRestaurants(pageN, size);
    }


}