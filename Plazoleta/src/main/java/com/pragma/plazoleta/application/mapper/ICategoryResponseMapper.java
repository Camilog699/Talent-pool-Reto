package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.CategoryResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {
    CategoryResponseDto toResponse(Category category);

    List<CategoryResponseDto> toResponseList(List<Category> categoryList);
}
