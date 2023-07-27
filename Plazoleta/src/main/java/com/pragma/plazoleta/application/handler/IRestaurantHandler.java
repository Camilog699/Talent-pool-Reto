package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.AllRestaurantResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantHandler {

    RestaurantResponseDto saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    List<AllRestaurantResponseDto> getAllRestaurants(ListPaginationRequest listPaginationRequest);

    RestaurantResponseDto getRestaurantById(Long id);

}