package com.pragma.plazoleta.infrastructure.input.rest.client;

import com.pragma.plazoleta.application.dto.request.TwilioRequestDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "Twilio", path = "/api/v1/twilio", url = "http://localhost:8083")
public interface ITwilioFeignClient {
    @PostMapping("/")
    public ResponseEntity<ResponseDto> sendMessage(@Valid @RequestBody TwilioRequestDto twilioRequestDto);


}