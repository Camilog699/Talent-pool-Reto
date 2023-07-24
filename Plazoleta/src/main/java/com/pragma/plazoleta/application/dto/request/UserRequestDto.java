package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String phone;
    private String email;
    private String password;
    private RoleRequestDto roleId;
}
