package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IDishHandler {


    DishResponseDto saveDish(DishRequestDto dishRequestDto);

    List<DishResponseDto> getAllDishes();

    DishResponseDto getDishById(Long id);

    DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto);

}