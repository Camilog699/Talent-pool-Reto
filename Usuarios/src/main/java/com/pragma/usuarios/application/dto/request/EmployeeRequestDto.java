package com.pragma.usuarios.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDto {
    private Long restaurantId;
    private Long employeeId;
    private String field;
    private Long ownerId;
}