package com.pragma.plazoleta.infrastructure.out.jpa.repository;

import com.pragma.plazoleta.infrastructure.out.jpa.entity.EmployeeEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    EmployeeEntity findRestaurantByEmployeeId(Long employeeId);

}