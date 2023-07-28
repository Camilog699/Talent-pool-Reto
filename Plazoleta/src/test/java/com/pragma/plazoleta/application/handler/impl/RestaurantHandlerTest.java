package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.ResponseClientDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.application.handler.impl.factory.FactoryRestaurantDataTest;
import com.pragma.plazoleta.application.mapper.IRestaurantRequestMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.exception.OwnerIdNotFoundException;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RestaurantHandlerTest {
    @InjectMocks
    RestaurantHandler restaurantHandler;
    @Mock
    IRestaurantServicePort restaurantServicePort;
    @Mock
    IRestaurantRequestMapper restaurantRequestMapper;
    @Mock
    IRestaurantResponseMapper restaurantResponseMapper;
    @Mock
    IUserFeignClient userClient;

    @Test
    void mustSaveARestaurant() {
        Restaurant expectedRestaurant = FactoryRestaurantDataTest.getRestaurantModel();

        RestaurantRequestDto restaurantRequestDto = FactoryRestaurantDataTest.getRestaurantRequestDto();

        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();

        RestaurantResponseDto restaurantResponseDto = FactoryRestaurantDataTest.getRestaurantResponseDto();


        when(restaurantRequestMapper.toRestaurant(any())).thenReturn(expectedRestaurant);
        when(userClient.getUserById(any())).thenReturn(response);
        when(restaurantResponseMapper.toResponse(any())).thenReturn(restaurantResponseDto);
        when(restaurantServicePort.saveRestaurant(any())).thenReturn(expectedRestaurant);
        when(restaurantServicePort.getById(any())).thenReturn(expectedRestaurant);

        Assertions.assertEquals(restaurantResponseDto, restaurantHandler.saveRestaurant(restaurantRequestDto));

        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void invalidPhoneFormat() {
        Validator validator = FactoryRestaurantDataTest.getValidator();

        RestaurantRequestDto restaurantInvalidPhoneFormat = FactoryRestaurantDataTest.getRestaurantBadPhoneNumber();

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantInvalidPhoneFormat);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidRequestNameMustBeNotNull() {
        Validator validator = FactoryRestaurantDataTest.getValidator();

        RestaurantRequestDto restaurantWithoutName = FactoryRestaurantDataTest.getRestaurantWithoutName();

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantWithoutName);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidRequestNameAllNumericName() {
        Validator validator = FactoryRestaurantDataTest.getValidator();

        RestaurantRequestDto restaurantWhitAllNumberName = FactoryRestaurantDataTest.getRestaurantInvalidName();

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantWhitAllNumberName);

        assertFalse(violations.isEmpty());
    }

    @Test
    void throwNoDataFoundExceptionWhereGetUserIsNull() {
        Restaurant expectedRestaurant = FactoryRestaurantDataTest.getRestaurantModel();
        RestaurantRequestDto restaurantRequestDto = FactoryRestaurantDataTest.getRestaurantRequestDto();

        when(restaurantRequestMapper.toRestaurant(any())).thenReturn(expectedRestaurant);
        when(userClient.getUserById(any())).thenReturn(null);

        Assertions.assertThrows(
                OwnerIdNotFoundException.class,
                () -> restaurantHandler.saveRestaurant(restaurantRequestDto)
        );
    }

}