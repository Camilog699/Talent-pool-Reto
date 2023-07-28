package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IRestaurantServicePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant getById(Long userId);

    List<Restaurant> getAllRestaurants();
    List<Restaurant> getAllRestaurants(int pageN, int size);
}