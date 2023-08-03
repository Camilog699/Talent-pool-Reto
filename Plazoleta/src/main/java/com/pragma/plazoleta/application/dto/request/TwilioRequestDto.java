package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TwilioRequestDto {
    @NotNull(message="The field 'number' is required")
    private String number;
    @NotNull(message="The field 'message' is required")
    private String message;
}
