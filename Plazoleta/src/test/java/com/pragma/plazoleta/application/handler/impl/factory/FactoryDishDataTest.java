package com.pragma.plazoleta.application.handler.impl.factory;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.CategoryResponseDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;

public class FactoryDishDataTest {

    public static Dish getDishModel() {
        Dish expectedDishModel = new Dish();

        expectedDishModel.setName("Arroz");
        expectedDishModel.setCategoryId(getCategoryModel());
        expectedDishModel.setDescription("restaurante");
        expectedDishModel.setPrice(200L);
        expectedDishModel.setRestaurantId(getRestaurantModel());
        expectedDishModel.setUrlImage("urlImage");
        expectedDishModel.setActive(true);

        return expectedDishModel;
    }

    public static Dish getDishModel2() {
        Dish expectedDishModel = new Dish();

        expectedDishModel.setName("Arroz");
        expectedDishModel.setCategoryId(getCategoryModel());
        expectedDishModel.setDescription("restaurante2");
        expectedDishModel.setPrice(200L);
        expectedDishModel.setRestaurantId(getRestaurantModel());
        expectedDishModel.setUrlImage("urlImage");
        expectedDishModel.setActive(true);

        return expectedDishModel;
    }

    public static DishRequestDto getDishRequestDto() {
        DishRequestDto dishRequestDto = new DishRequestDto();

        dishRequestDto.setName("Prueba 1");
        dishRequestDto.setCategoryId(1L);
        dishRequestDto.setDescription("Updating");
        dishRequestDto.setPrice(5501L);
        dishRequestDto.setRestaurantId(1L);
        dishRequestDto.setUrlImage("string");

        return dishRequestDto;
    }

    public static Category getCategoryModel() {
        Category categoryModel = new Category();

        categoryModel.setId(1L);
        categoryModel.setName("categoria");
        categoryModel.setDescription("descripcion");

        return categoryModel;
    }

    public static Restaurant getRestaurantModel() {
        Restaurant restaurantModel = new Restaurant();

        restaurantModel.setId(1L);
        restaurantModel.setName("Subway");
        restaurantModel.setAddress("Calle 5");
        restaurantModel.setOwnerId(1L);
        restaurantModel.setPhone("+10000");
        restaurantModel.setUrlLogo("logoUrl");
        restaurantModel.setNit("20000");

        return restaurantModel;
    }

    public static Restaurant getRestaurantModel2() {
        Restaurant restaurantModel = new Restaurant();

        restaurantModel.setId(1L);
        restaurantModel.setName("Subway");
        restaurantModel.setAddress("Calle 5");
        restaurantModel.setOwnerId(2L);
        restaurantModel.setPhone("+10000");
        restaurantModel.setUrlLogo("logoUrl");
        restaurantModel.setNit("20000");

        return restaurantModel;
    }

    public static DishResponseDto getDishResponseDto() {
        DishResponseDto dishResponseDto = new DishResponseDto();

        dishResponseDto.setName("Arroz");
        dishResponseDto.setCategoryId(getCategoryResponseDto());
        dishResponseDto.setDescription("restaurante");
        dishResponseDto.setPrice(200L);
        dishResponseDto.setRestaurantId(getRestaurantResponseDto());
        dishResponseDto.setUrlImage("urlImage");
        dishResponseDto.setActive(true);

        return dishResponseDto;
    }

    public static CategoryResponseDto getCategoryResponseDto() {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setName("categoria");
        categoryResponseDto.setDescription("descripcion");
        return categoryResponseDto;
    }


    public static RestaurantResponseDto getRestaurantResponseDto() {
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

        restaurantResponseDto.setName("Subway");
        restaurantResponseDto.setAddress("Calle 5");
        restaurantResponseDto.setOwnerId(3l);
        restaurantResponseDto.setPhone("+10000");
        restaurantResponseDto.setUrlLogo("logoUrl");
        restaurantResponseDto.setNit("20000");

        return restaurantResponseDto;
    }

    public static Restaurant getRestaurantModelIncorrectId() {
        Restaurant restaurantModel = new Restaurant();

        restaurantModel.setId(1L);
        restaurantModel.setName("Subway");
        restaurantModel.setAddress("Calle 5");
        restaurantModel.setOwnerId(3l);
        restaurantModel.setPhone("+10000");
        restaurantModel.setUrlLogo("logoUrl");
        restaurantModel.setNit("20000");

        return restaurantModel;
    }

    /**
    public static DishUpdateRequestDto getDishUpdateRequest(){
        DishUpdateRequestDto dishUpdateRequestDto = new DishUpdateRequestDto();

        dishUpdateRequestDto.setId(1L);
        dishUpdateRequestDto.setDescription("restaurante2");
        dishUpdateRequestDto.setPrice(300);

        return dishUpdateRequestDto;
    }
     */

    public static DishResponseDto getDishUpdateResponseDto() {
        DishResponseDto dishResponseDto = new DishResponseDto();

        dishResponseDto.setName("Arroz");
        dishResponseDto.setCategoryId(getCategoryResponseDto());
        dishResponseDto.setDescription("restaurante2");
        dishResponseDto.setPrice(300L);
        dishResponseDto.setRestaurantId(getRestaurantResponseDto());
        dishResponseDto.setUrlImage("urlImage");
        dishResponseDto.setActive(true);

        return dishResponseDto;
    }

    public static UserRequestDto getUserRequestDto(){
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(3L);
        return userRequestDto;
    }

}