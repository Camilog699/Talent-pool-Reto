package com.pragma.plazoleta.infrastructure.input.rest.client;

import com.pragma.plazoleta.application.dto.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Usuario", path = "/api/v1/user", url = "http://localhost:8081")
public interface IUserFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<ResponseDto> getUserById(@PathVariable Long idUser);

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDto> getByEmail(@PathVariable String email);

}