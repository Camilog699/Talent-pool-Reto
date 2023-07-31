package com.pragma.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class OrderResponseDto {
    private Long idClient;
    private Date date;
    private String status;
    private String urlImage;
    private Long idChief;
    private RestaurantResponseDto idRestaurant;
}
