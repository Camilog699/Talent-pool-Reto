package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private boolean error;
    private String message;
    private UserRequestDto data;

}