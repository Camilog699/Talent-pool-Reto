package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DishUpdateRequestDto {
    private Long price;
    private String description;
}
