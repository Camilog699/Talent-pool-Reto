package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.EmployeeRequestDto;
import com.pragma.plazoleta.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRequestMapper {
    @Mapping(source = "employeeRequestDto.restaurantId", target = "restaurantId.id")
    @Mapping(source = "employeeRequestDto.employeeId", target = "employeeId.id")
    Employee toEmployee(EmployeeRequestDto employeeRequestDto);
}
