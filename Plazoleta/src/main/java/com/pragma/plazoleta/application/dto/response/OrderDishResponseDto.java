package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishResponseDto {
    private DishResponseDto dishId;
    private Integer quantity;
}
