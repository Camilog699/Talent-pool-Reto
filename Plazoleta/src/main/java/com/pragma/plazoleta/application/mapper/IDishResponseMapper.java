package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.CategoryResponseDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ICategoryResponseMapper.class, IRestaurantResponseMapper.class})
public interface IDishResponseMapper {

    IRestaurantResponseMapper RESTAURANT_INSTANCE = Mappers.getMapper(IRestaurantResponseMapper.class);
    ICategoryResponseMapper CATEGORY_INSTANCE = Mappers.getMapper(ICategoryResponseMapper.class);
    @Mapping(target = "categoryId.name", source = "categoryResponseDto.name")
    @Mapping(target = "categoryId.description", source = "categoryResponseDto.description")
    @Mapping(target = "restaurantId.name", source = "restaurantResponseDto.name")
    @Mapping(target = "restaurantId.address", source = "restaurantResponseDto.address")
    @Mapping(target = "restaurantId.ownerId", source = "restaurantResponseDto.ownerId")
    @Mapping(target = "restaurantId.phone", source = "restaurantResponseDto.phone")
    @Mapping(target = "restaurantId.urlLogo", source = "restaurantResponseDto.urlLogo")
    @Mapping(target = "restaurantId.nit", source = "restaurantResponseDto.nit")
    @Mapping(target = "name", source = "dish.name")
    @Mapping(target = "description", source = "dish.description")
    DishResponseDto toResponse(Dish dish, CategoryResponseDto categoryResponseDto, RestaurantResponseDto restaurantResponseDto);

    default List<DishResponseDto> toResponseList(List<Dish> dishModelList, List<Category> categoryModelList, List<Restaurant> restaurantModelList) {
        return dishModelList.stream()
                .map(dish -> {
                    DishResponseDto dishResponseDto = new DishResponseDto();

                    dishResponseDto.setName(dish.getName());
                    dishResponseDto.setCategoryId(CATEGORY_INSTANCE.toResponse(categoryModelList.stream().filter(
                            category -> category.getId().equals(dish.getCategoryId().getId())
                    ).findFirst().get()));

                    dishResponseDto.setDescription(dish.getDescription());
                    dishResponseDto.setPrice(dish.getPrice());

                    dishResponseDto.setRestaurantId(RESTAURANT_INSTANCE.toResponse(restaurantModelList.stream().filter(
                            restaurant -> restaurant.getId().equals(dish.getRestaurantId().getId())
                    ).findFirst().get()));

                    dishResponseDto.setUrlImage(dish.getUrlImage());
                    dishResponseDto.setActive(dish.getActive());

                    return dishResponseDto;
                }).collect(Collectors.toList());
    }
}
