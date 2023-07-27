package com.pragma.plazoleta.infrastructure.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.Employee;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.EmployeeEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IEmployeeEntityMapper {

    @Mapping(source = "employee.restaurantId.id", target = "restaurantId.id")
    @Mapping(source = "employee.employeeId.id", target = "employeeId")
    EmployeeEntity toEntity(Employee employee);

    @Mapping(source = "employeeEntity.employeeId", target = "employeeId.id")
    Employee toObjectModel(EmployeeEntity employeeEntity);
    //List<EmployeeEntity> toObjectModelList(List<EmployeeEntity> employeeEntityList);
}