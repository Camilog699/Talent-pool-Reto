package com.pragma.usuarios.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String phone;
    private Date dateBirth;
    private String email;
    private String password;
    private RoleDto roleId;
}
