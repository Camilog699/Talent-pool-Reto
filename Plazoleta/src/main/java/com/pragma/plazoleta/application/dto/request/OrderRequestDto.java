package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Long restaurantId;
    private List<OrderDishRequestDto> dishes;
}
