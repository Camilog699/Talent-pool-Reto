package com.pragma.usuarios.infrastructure.input.rest.Plazoleta;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "Plazoleta", path = "/api/v1/employee", url = "http://localhost:8082")
public interface IPlazoletaFeignClient {
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> saveRestaurantEmployee(@RequestBody EmployeeRequestDto employeeRequestDto);

}
