package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Employee;


public interface IEmployeePersistencePort {
    Employee saveEmployee(Employee employee);

    Employee getById(Long userId);
}