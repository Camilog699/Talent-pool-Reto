package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Employee;

public interface IEmployeeServicePort {

    Employee saveEmployee(Employee employee);

    Employee getById(Long employeeId);

}