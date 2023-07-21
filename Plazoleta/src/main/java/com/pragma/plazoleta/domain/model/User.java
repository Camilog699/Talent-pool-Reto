package com.pragma.plazoleta.domain.model;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String phone;
    private Date dateBirth;
    private String email;
    private String password;
    private Role roleId;
}