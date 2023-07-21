package com.pragma.plazoleta.infrastructure.input.rest.client;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuarios", path = "/api/v1/user", url = "http://localhost:8081")
public interface IUserFeignClient {
    @PostMapping("/create")
    public ResponseEntity<Void> register(@RequestBody UserRequestDto userRequestDto);

}