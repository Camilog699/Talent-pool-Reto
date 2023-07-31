package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IEmployeeServicePort;
import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IEmployeePersistencePort;

public class EmployeeUseCase implements IEmployeeServicePort {

    private final IEmployeePersistencePort employeePersistencePort;

    public EmployeeUseCase(IEmployeePersistencePort employeePersistencePort) {
        this.employeePersistencePort = employeePersistencePort;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeePersistencePort.saveEmployee(employee);
    }

    @Override
    public Employee getById(Long employeeId) {
        return employeePersistencePort.getById(employeeId);
    }

    @Override
    public Employee getRestaurantByEmployeeId(Long employeeId) {
        return employeePersistencePort.getRestaurantByEmployeeId(employeeId);
    }

}