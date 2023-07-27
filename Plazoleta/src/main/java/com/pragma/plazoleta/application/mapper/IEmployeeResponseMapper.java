package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.EmployeeResponseDto;
import com.pragma.plazoleta.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeResponseMapper {

    @Mapping(source = "employee.restaurantId.id", target = "restaurantId")
    @Mapping(source = "employee.employeeId.id", target = "employeeId")
    EmployeeResponseDto toResponse(Employee employee);

    List<EmployeeResponseDto> toResponseList(List<Employee> employeeList);
}
