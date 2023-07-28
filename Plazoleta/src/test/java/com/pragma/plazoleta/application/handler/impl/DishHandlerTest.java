package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.response.CategoryResponseDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseClientDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.application.handler.impl.factory.FactoryDishDataTest;
import com.pragma.plazoleta.application.handler.impl.factory.FactoryRestaurantDataTest;
import com.pragma.plazoleta.application.mapper.*;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.infrastructure.exception.NotUpdatableFieldsException;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class DishHandlerTest {
    @InjectMocks
    DishHandler dishHandler;
    @Mock
    IDishServicePort dishServicePort;
    @Mock
    IDishRequestMapper dishRequestMapper;
    @Mock
    IDishResponseMapper dishResponseMapper;
    @Mock
    ICategoryServicePort categoryServicePort;
    @Mock
    ICategoryResponseMapper categoryResponseMapper;
    @Mock
    IRestaurantServicePort restaurantServicePort;
    @Mock
    IRestaurantResponseMapper restaurantResponseMapper;
    @Mock
    IUserFeignClient userClient;

    @Test
    void mustSaveADish() {
        DishRequestDto dishRequestDto = FactoryDishDataTest.getDishRequestDto();
        Dish dishModel = FactoryDishDataTest.getDishModel();
        DishResponseDto dishResponseDto = FactoryDishDataTest.getDishResponseDto();
        Category categoryModel = FactoryDishDataTest.getCategoryModel();
        Restaurant restaurantModel = FactoryDishDataTest.getRestaurantModel();
        CategoryResponseDto categoryResponseDto = FactoryDishDataTest.getCategoryResponseDto();
        RestaurantResponseDto restaurantResponseDto = FactoryDishDataTest.getRestaurantResponseDto();

        when(categoryServicePort.getById(any())).thenReturn(categoryModel);
        when(restaurantServicePort.getById(any())).thenReturn(restaurantModel);
        when(dishRequestMapper.toDish(dishRequestDto)).thenReturn(dishModel);
        when(categoryResponseMapper.toResponse(any())).thenReturn(categoryResponseDto);
        when(restaurantResponseMapper.toResponse(any())).thenReturn(restaurantResponseDto);
        when(dishResponseMapper.toResponse(any(), any(), any())).thenReturn(dishResponseDto);

        Assertions.assertEquals(dishResponseDto, dishHandler.saveDish(dishRequestDto));

        verify(dishServicePort).saveDish(any(Dish.class));
    }

    @Test
    void throwNotEnoughPrivilegesWhereGetUserIsNotEqualsToOwnerId() {
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        Restaurant restaurantModelIncorrectId = FactoryDishDataTest.getRestaurantModelIncorrectId();
        DishRequestDto dishRequestDto = FactoryDishDataTest.getDishRequestDto();

        when(userClient.getUserById(any())).thenReturn(response);
        when(restaurantServicePort.getById(any())).thenReturn(restaurantModelIncorrectId);

        Assertions.assertThrows(
                NotEnoughPrivilegesException.class,
                () -> dishHandler.saveDish(dishRequestDto)
        );
    }



    @Test
    void mustUpdateADish() {
        Dish dish = FactoryDishDataTest.getDishModel();
        DishUpdateRequestDto dishRequestDto = FactoryDishDataTest.getDishUpdateRequest();
        CategoryResponseDto categoryResponseDto = FactoryDishDataTest.getCategoryResponseDto();
        RestaurantResponseDto restaurantResponseDto = FactoryDishDataTest.getRestaurantResponseDto();
        DishResponseDto dishResponseDto = FactoryDishDataTest.getDishUpdateResponseDto();

            when(dishServicePort.getById(any())).thenReturn(dish);
            when(restaurantServicePort.getById(any())).thenReturn(dish.getRestaurantId());
            when(categoryResponseMapper.toResponse(any())).thenReturn(categoryResponseDto);
            when(restaurantResponseMapper.toResponse(any())).thenReturn(restaurantResponseDto);
            when(dishResponseMapper.toResponse(any(), any(), any())).thenReturn(dishResponseDto);

            Assertions.assertThrows(NotUpdatableFieldsException.class, () -> dishHandler.updateDish(1L, dishRequestDto)
            );
    }

    @Test
    void mustGetAllDishesByRestaurantId() {
        List<Dish> dishModelList = new ArrayList<>();
        dishModelList.add(FactoryDishDataTest.getDishModel());

        List<Category> categoryModelList = new ArrayList<>();
        categoryModelList.add(FactoryDishDataTest.getCategoryModel());

        List<Restaurant> restaurantModelList = new ArrayList<>();
        restaurantModelList.add(FactoryDishDataTest.getRestaurantModel());

        List<DishResponseDto> dishResponseDtos = new ArrayList<>();
        dishResponseDtos.add(FactoryDishDataTest.getDishResponseDto());

        ListPaginationRequest listPaginationRequest = new ListPaginationRequest();
        listPaginationRequest.setPageN(0);
        listPaginationRequest.setSize(20);

        when(dishServicePort.getDishByRestaurantId(0, 20, 3L)).thenReturn(dishModelList);
        when(categoryServicePort.getAllCategories()).thenReturn(categoryModelList);
        when(restaurantServicePort.getAllRestaurants()).thenReturn(restaurantModelList);

        Assertions.assertEquals(dishResponseDtos, dishHandler.getDishByRestaurantId(listPaginationRequest, 1L));

        verify(dishResponseMapper).toResponseList(dishModelList, categoryModelList, restaurantModelList);
    }

}