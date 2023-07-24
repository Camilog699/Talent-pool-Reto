package com.pragma.plazoleta.application.dto.request;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DishRequestDto {
    @NotNull(message = "El nombre del plato no puede ser nulo")
    private String name;
    @NotNull(message = "El precio del plato no puede ser nulo")
    private Long price;
    private String description;
    private String urlImage;
    @NotNull(message = "El id de la categoria no puede ser nulo")
    private Long categoryId;
    @NotNull(message = "El id del restaurante no puede ser nulo")
    private Long restaurantId;
    private Boolean active;
}
