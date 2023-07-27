package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListPaginationRequest {
    @NotNull(message = "El campo de numero de pagina no puede ser nulo")
    @Min(value = 0)
    private Integer pageN;
    @NotNull(message = "El campo de tamaño de pagina no puede ser nulo")
    @Min(value = 1, message = "El valor minimo para el campo es de 1")
    private Integer size;
}