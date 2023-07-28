package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;

import java.util.List;

public interface IDishHandler {


    DishResponseDto saveDish(DishRequestDto dishRequestDto);

    List<DishResponseDto> getAllDishes();

    DishResponseDto updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto);

    DishResponseDto enableDish(Long id);

    DishResponseDto getDishById(Long id);

    List<DishResponseDto> getDishByRestaurantId(ListPaginationRequest listPaginationRequest,Long id);

}