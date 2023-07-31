package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDishRequestDto {
    private Long dishId;
    private Integer quantity;
}
