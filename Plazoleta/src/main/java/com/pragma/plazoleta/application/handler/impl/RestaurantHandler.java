package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.application.mapper.IRestaurantRequestMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IUserFeignClient userFeignClient;
    @Override
    public RestaurantResponseDto saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
        Object user = userFeignClient.getUserById(restaurant.getOwnerId());
        if (user == null) {
            throw new RuntimeException("El usuario no existe");
        }
        restaurant.setOwnerId(restaurantRequestDto.getOwnerId());
        return restaurantResponseMapper.toResponse(restaurantServicePort.saveRestaurant(restaurant));
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        return restaurantResponseMapper.toResponse(restaurantServicePort.getById(id));
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants());
    }
}