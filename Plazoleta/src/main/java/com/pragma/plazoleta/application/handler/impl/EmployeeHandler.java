package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.EmployeeRequestDto;
import com.pragma.plazoleta.application.dto.response.EmployeeResponseDto;
import com.pragma.plazoleta.application.handler.IEmployeeHandler;
import com.pragma.plazoleta.application.mapper.IEmployeeRequestMapper;
import com.pragma.plazoleta.application.mapper.IEmployeeResponseMapper;
import com.pragma.plazoleta.domain.api.IEmployeeServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.common.exception.OwnerIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeHandler implements IEmployeeHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IEmployeeRequestMapper employeeRequestMapper;
    private final IEmployeeResponseMapper employeeResponseMapper;
    private final IEmployeeServicePort employeeServicePort;

    @Override
    public EmployeeResponseDto saveRestaurantEmployee(EmployeeRequestDto employeeRequestDto) {
        Restaurant restaurant = restaurantServicePort.getById(employeeRequestDto.getRestaurantId());
        if(!restaurant.getOwnerId().equals(employeeRequestDto.getOwnerId())){
            throw new OwnerIdNotFoundException();
        }
        Employee employee = employeeRequestMapper.toEmployee(employeeRequestDto);
        Employee employeePort = employeeServicePort.saveEmployee(employee);
        return employeeResponseMapper.toResponse(employeePort);
    }

    @Override
    public EmployeeResponseDto getById(Long employeeId) {
        Employee employee = employeeServicePort.getById(employeeId);
        return employeeResponseMapper.toResponse(employee);
    }
}