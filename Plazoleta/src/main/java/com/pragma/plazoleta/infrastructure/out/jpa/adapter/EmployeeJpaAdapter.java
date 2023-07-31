package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.common.exception.RestaurantEmployeeNotFoundException;
import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.domain.spi.IEmployeePersistencePort;
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.EmployeeEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IEmployeeEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeJpaAdapter implements IEmployeePersistencePort {

    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;


    @Override
    public Employee saveEmployee(Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.save(employeeEntityMapper.toEntity(employee));
        return employeeEntityMapper.toObjectModel(employeeEntity);
    }

    @Override
    public Employee getById(Long employeeId) {
        return employeeEntityMapper.toObjectModel(employeeRepository.findById(employeeId).orElseThrow(RestaurantEmployeeNotFoundException::new));
    }

    @Override
    public Employee getRestaurantByEmployeeId(Long employeeId) {
        return employeeEntityMapper.toObjectModel(employeeRepository.findRestaurantByEmployeeId(employeeId));
    }

}