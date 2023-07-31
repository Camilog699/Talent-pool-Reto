package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.EmployeeRequestDto;
import com.pragma.plazoleta.application.dto.response.EmployeeResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.handler.IEmployeeHandler;
import com.pragma.plazoleta.common.exception.NotEnoughPrivilegesException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final IEmployeeHandler employeeHandler;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> saveRestaurantEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        ResponseDto responseDto = new ResponseDto();

        try {
            EmployeeResponseDto EmployeeResponseDto = employeeHandler.saveRestaurantEmployee(employeeRequestDto);

            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(EmployeeResponseDto);
        } catch (NotEnoughPrivilegesException ex) {
            responseDto.setError(true);
            responseDto.setMessage("El usuario no es dueño del restaurante");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}