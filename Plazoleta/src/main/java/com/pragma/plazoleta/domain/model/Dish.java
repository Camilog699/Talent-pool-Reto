package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dish {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String urlImage;
    private Category categoryId;
    private Restaurant restaurantId;
    private Boolean active;
}
