package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RestaurantRequestDto {
    @NotNull(message = "Nombre de restaurante es obligatorio")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[\\w\\d]*[a-zA-Z]+[\\w\\d]*$", message = "El nombre debe contener al menos una letra y puede incluir números")
    private String name;

    @NotNull(message = "Direccion es obligatoria")
    private String address;

    @NotNull(message = "Id del propietario es obligatorio")
    private Long ownerId;

    @NotNull(message = "Telefono es obligatorio")
    @Size(max = 13, message = "El numero de telefono tiene un maximo de 13 caracteres")
    @Pattern(regexp = "[+]?\\d+(\\d+)?", message = "Formato incorrecto de numero de telefono")
    private String phone;

    @NotNull(message = "url del logo es obligatorio")
    private String urlLogo;

    @NotNull(message = "Número de identificación es obligatorio")
    @Pattern(regexp = "\\d+(\\d+)?", message = "El numero de identificacion debe ser numerico")
    private String nit;
}
