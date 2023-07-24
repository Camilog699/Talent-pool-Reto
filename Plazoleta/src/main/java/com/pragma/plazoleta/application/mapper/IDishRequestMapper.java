package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {
    //Tengo la duda de si este mapeo puede reemplazar el funcionamiento de la clase DishHandler
    @Mapping(source = "dishRequestDto.restaurantId", target = "restaurantId.id")
    @Mapping(source = "dishRequestDto.categoryId", target = "categoryId.id")
    Dish toDish(DishRequestDto dishRequestDto);
}
