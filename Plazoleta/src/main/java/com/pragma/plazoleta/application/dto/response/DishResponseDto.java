package com.pragma.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DishResponseDto {
    private String name;
    private Long price;
    private String description;
    private String urlImage;
    private CategoryResponseDto categoryId;
    private RestaurantResponseDto restaurantId;
    private Boolean active;
}
