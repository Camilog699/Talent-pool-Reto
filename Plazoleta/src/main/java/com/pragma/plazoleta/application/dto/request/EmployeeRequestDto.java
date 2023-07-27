package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDto {
    private Long employeeId;
    private Long restaurantId;
    private String field;
    private Long ownerId;
}
