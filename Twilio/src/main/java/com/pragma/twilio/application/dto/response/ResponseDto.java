package com.pragma.twilio.application.dto.response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private Boolean error;
    private String message;
    private Object data;
}