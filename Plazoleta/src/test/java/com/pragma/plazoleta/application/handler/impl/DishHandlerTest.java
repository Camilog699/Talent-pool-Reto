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
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.common.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.common.exception.NotUpdatableFieldsException;
import com.pragma.plazoleta.infrastructure.configuration.FeignClientInterceptorImp;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

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
    @Mock
    JwtHandler jwtHandler;

    @Test
    void mustSaveADish() {
        DishRequestDto dishRequestDto = FactoryDishDataTest.getDishRequestDto();
        Dish dishModel = FactoryDishDataTest.getDishModel();
        DishResponseDto dishResponseDto = FactoryDishDataTest.getDishResponseDto();
        Category categoryModel = FactoryDishDataTest.getCategoryModel();
        Restaurant restaurantModel = FactoryDishDataTest.getRestaurantModel();
        CategoryResponseDto categoryResponseDto = FactoryDishDataTest.getCategoryResponseDto();
        RestaurantResponseDto restaurantResponseDto = FactoryDishDataTest.getRestaurantResponseDto();
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();

        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            when(userClient.getUserById(any())).thenReturn(response);
            when(categoryServicePort.getById(any())).thenReturn(categoryModel);
            when(restaurantServicePort.getById(any())).thenReturn(restaurantModel);
            when(dishRequestMapper.toDish(dishRequestDto)).thenReturn(dishModel);
            when(categoryResponseMapper.toResponse(any())).thenReturn(categoryResponseDto);
            when(restaurantResponseMapper.toResponse(any())).thenReturn(restaurantResponseDto);
            when(dishResponseMapper.toResponse(any(), any(), any())).thenReturn(dishResponseDto);

            Assertions.assertEquals(dishResponseDto, dishHandler.saveDish(dishRequestDto));

            verify(dishServicePort).saveDish(any(Dish.class));
        }
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
        DishResponseDto dishResponseDto = FactoryDishDataTest.getDishUpdateResponseDto();
        Dish dish = FactoryDishDataTest.getDishModel();
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        DishUpdateRequestDto dishRequestDto = FactoryDishDataTest.getDishUpdateRequest();
        CategoryResponseDto categoryResponseDto = FactoryDishDataTest.getCategoryResponseDto();
        RestaurantResponseDto restaurantResponseDto = FactoryDishDataTest.getRestaurantResponseDto();

        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            when(userClient.getByEmail(any())).thenReturn(response);
            when(jwtHandler.extractUserName(any())).thenReturn("email@gmail.com");
            when(dishServicePort.getById(any())).thenReturn(dish);
            when(restaurantServicePort.getById(any())).thenReturn(dish.getRestaurantId());
            when(categoryResponseMapper.toResponse(any())).thenReturn(categoryResponseDto);
            when(restaurantResponseMapper.toResponse(any())).thenReturn(restaurantResponseDto);
            when(dishResponseMapper.toResponse(any(), any(), any())).thenReturn(dishResponseDto);

            Assertions.assertEquals(dishResponseDto, dishHandler.updateDish(1L, dishRequestDto)
            );
        }
    }

    @Test
    void mustEnableOrDisableADish() {
        Dish dish = FactoryDishDataTest.getDishModel();
        DishResponseDto dishResponseDto = FactoryDishDataTest.getDishResponseDto();
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        Restaurant restaurantModel = FactoryDishDataTest.getRestaurantModel();

        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            when(userClient.getByEmail(any())).thenReturn(response);
            when(jwtHandler.extractUserName(any())).thenReturn("amraga10@gmail.com");
            when(dishServicePort.getById(any())).thenReturn(dish);
            when(restaurantServicePort.getById(any())).thenReturn(restaurantModel);

            when(dishResponseMapper.toResponse(any(), any(), any())).thenReturn(dishResponseDto);

            Assertions.assertEquals(dishResponseDto, dishHandler.enableDish(1L));
        }
    }

    @Test
    void throwNotEnoughPrivilegesWhereUserEnableDishIsNotEqualsToOwnerId() {
        Dish dish = FactoryDishDataTest.getDishModel();
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        Restaurant restaurantModelIncorrectId = FactoryDishDataTest.getRestaurantModelIncorrectId();

        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            when(userClient.getByEmail(any())).thenReturn(response);
            when(jwtHandler.extractUserName(any())).thenReturn("camilo@gmail.com");

            when(dishServicePort.getById(any())).thenReturn(dish);
            when(restaurantServicePort.getById(any())).thenReturn(restaurantModelIncorrectId);

            Assertions.assertThrows(
                    NotEnoughPrivilegesException.class,
                    () -> dishHandler.enableDish(1L)
            );
        }
    }

    @Test
    void mustGetDishById(){
        DishResponseDto dishResponseDto = FactoryDishDataTest.getDishResponseDto();
        Dish dish = FactoryDishDataTest.getDishModel();
        Restaurant restaurantModel = FactoryDishDataTest.getRestaurantModel();
        Category categoryModel = FactoryDishDataTest.getCategoryModel();

        when(dishServicePort.getById(any())).thenReturn(dish);
        when(restaurantServicePort.getById(any())).thenReturn(restaurantModel);
        when(categoryServicePort.getById(any())).thenReturn(categoryModel);
        when(dishResponseMapper.toResponse(any(), any(), any())).thenReturn(dishResponseDto);

        Assertions.assertEquals(dishResponseDto, dishHandler.getDishById(1L));
    }

    @Test
    void throwDishNotFoundExceptionWhereDishIdDoesNotExist(){
        when(dishServicePort.getById(any())).thenThrow(NotFoundException.class);

        Assertions.assertThrows(
                NotFoundException.class,
                () -> dishHandler.getDishById(1L)
        );
    }

    @Test
    void mustGetAllDishes(){
        List<DishResponseDto> dishResponseDto = new ArrayList<>();
        dishResponseDto.add(FactoryDishDataTest.getDishResponseDto());

        when(dishResponseMapper.toResponseList(dishServicePort.getAllDishes(),categoryServicePort.getAllCategories(), restaurantServicePort.getAllRestaurants())).thenReturn(dishResponseDto);

        Assertions.assertEquals(dishResponseDto, dishHandler.getAllDishes());
    }

    /*
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
        listPaginationRequest.setPageN(1);
        listPaginationRequest.setSize(1);

        when(dishServicePort.getDishByRestaurantId(1, 2, 1L)).thenReturn(dishModelList);
        when(categoryServicePort.getAllCategories()).thenReturn(categoryModelList);
        when(restaurantServicePort.getAllRestaurants()).thenReturn(restaurantModelList);

        Assertions.assertEquals(dishResponseDtos, dishHandler.getDishByRestaurantId(listPaginationRequest, 1L));

        verify(dishResponseMapper).toResponseList(dishModelList, categoryModelList, restaurantModelList);
    }
     */

}