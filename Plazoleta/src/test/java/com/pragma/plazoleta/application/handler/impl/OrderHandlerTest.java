package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderStateResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseClientDto;
import com.pragma.plazoleta.application.handler.IOrderDishHandler;
import com.pragma.plazoleta.application.handler.impl.factory.FactoryDishDataTest;
import com.pragma.plazoleta.application.handler.impl.factory.FactoryOrderDataTest;
import com.pragma.plazoleta.application.handler.impl.factory.FactoryRestaurantDataTest;
import com.pragma.plazoleta.application.mapper.IOrderDishResponseMapper;
import com.pragma.plazoleta.application.mapper.IOrderResponseMapper;
import com.pragma.plazoleta.application.mapper.IUserRequestMapper;
import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.common.exception.DishNotFoundException;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderHandlerTest {
    @InjectMocks
    OrderHandler orderHandler;

    @Mock
    IRestaurantServicePort restaurantServicePort;
    @Mock
    JwtHandler jwtHandler;
    @Mock
    IUserFeignClient userClient;
    @Mock
    IOrderServicePort orderServicePort;
    @Mock
    IUserRequestMapper userRequestMapper;
    @Mock
    IOrderDishHandler orderDishHandler;
    @Mock
    IOrderResponseMapper orderResponseMapper;
    @Mock
    IOrderDishServicePort orderDishServicePort;
    @Mock
    IOrderDishResponseMapper orderDishResponseMapper;
    @Mock
    IEmployeeServicePort restaurantEmployeeServicePort;
    @Mock
    IDishServicePort dishServicePort;


    @Test
    void mustCreateAnOrder() {
        OrderRequestDto orderRequestDto = FactoryOrderDataTest.getOrderRequestDto();
        Restaurant restaurantModel = FactoryOrderDataTest.getRestaurantModel();
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        User userModel = FactoryOrderDataTest.getUserModel();
        Order orderModel = FactoryOrderDataTest.getOrderModel();
        OrderResponseDto orderResponseDto = FactoryOrderDataTest.getOrderResponseDto();
        OrderDishResponseDto orderDishResponseDto = FactoryOrderDataTest.getOrderDishResponseDto();
        Dish dishModel = FactoryDishDataTest.getDishModel();

        Mockito.when(restaurantServicePort.getById(any())).thenReturn(restaurantModel);
        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            Mockito.when(jwtHandler.extractUserName(any())).thenReturn("email@gmail.com");
            when(userClient.getByEmail(any())).thenReturn(response);
            //when(orderServicePort.getAllOrdersByUserIdOrderStateIn(any(), any())).thenReturn(true);
            when(dishServicePort.getById(anyLong())).thenReturn(dishModel);
            when(userRequestMapper.toUser(any())).thenReturn(userModel);
            when(orderServicePort.createOrder(any())).thenReturn(orderModel);
            when(orderResponseMapper.toResponse(any(), any())).thenReturn(orderResponseDto);
            when(orderDishHandler.createOrderDish(any(), anyLong())).thenReturn(orderDishResponseDto);

            Assertions.assertEquals(orderResponseDto, orderHandler.createOrder(orderRequestDto));

            verify(orderServicePort).createOrder(any(Order.class));
        }
    }

    @Test
    void throwDishNotFoundInRestaurantException() {
        OrderRequestDto orderRequestDto = FactoryOrderDataTest.getOrderRequestDto();
        Restaurant restaurantModel = FactoryOrderDataTest.getRestaurantModel();
        restaurantModel.setId(2L);
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        User userModel = FactoryOrderDataTest.getUserModel();
        Order orderModel = FactoryOrderDataTest.getOrderModel();
        OrderResponseDto orderResponseDto = FactoryOrderDataTest.getOrderResponseDto();
        Dish dishModel = FactoryDishDataTest.getDishModel();

        Mockito.when(restaurantServicePort.getById(any())).thenReturn(restaurantModel);
        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            Mockito.when(jwtHandler.extractUserName(any())).thenReturn("email@gmail.com");
            when(userClient.getByEmail(any())).thenReturn(response);
            //when(orderServicePort.getAllOrdersByUserIdOrderStateIn(any(), any())).thenReturn(true);
            when(userRequestMapper.toUser(any())).thenReturn(userModel);
            when(orderServicePort.createOrder(any())).thenReturn(orderModel);
            when(orderResponseMapper.toResponse(any(), any())).thenReturn(orderResponseDto);
            when(dishServicePort.getById(anyLong())).thenReturn(dishModel);

            Assertions.assertThrows(
                    DishNotFoundException.class,
                    () -> orderHandler.createOrder(orderRequestDto)
            );
        }
    }

    @Test
    void mustAssignAnOrder() {
        Long orderId = 1L;
        Order orderModel = FactoryOrderDataTest.getOrderModel();
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        User userModel = FactoryOrderDataTest.getUserModel();
        OrderDish orderDishModel = FactoryOrderDataTest.getOrderDishModel();
        OrderDishResponseDto orderDishResponseDto = FactoryOrderDataTest.orderDishResponseDto();
        OrderResponseDto orderResponseDto = FactoryOrderDataTest.getOrderResponseDto();
        orderResponseDto.setStatus(OrderState.EN_PREPARACION);

        when(orderServicePort.getById(anyLong())).thenReturn(orderModel);

        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            when(jwtHandler.extractUserName(any())).thenReturn("email@gmail.com");
            when(userClient.getByEmail(any())).thenReturn(response);
            when(userRequestMapper.toUser(any())).thenReturn(userModel);
            when(orderServicePort.createOrder(any())).thenReturn(orderModel);
            when(orderDishServicePort.getAllOrderDishByOrder(anyLong())).thenReturn(List.of(orderDishModel));
            when(orderDishResponseMapper.toResponse(any())).thenReturn(orderDishResponseDto);
            when(orderResponseMapper.toResponse(any(), any())).thenReturn(orderResponseDto);

            Assertions.assertEquals(orderResponseDto, orderHandler.assignOrderToEmployee(orderId));

            verify(orderServicePort).createOrder(any(Order.class));
        }
    }

    @Test
    void mustListAllOrdersByState() {
        OrderState orderState = OrderState.PENDIENTE;
        ResponseEntity<ResponseClientDto> response = FactoryRestaurantDataTest.getResponseEntity();
        Employee restaurantEmployeeModel = FactoryRestaurantDataTest.getRestaurantEmployeeModel();
        Restaurant restaurantModel = FactoryOrderDataTest.getRestaurantModel();
        OrderDish orderDishModel = FactoryOrderDataTest.getOrderDishModel();
        Order orderModel = FactoryOrderDataTest.getOrderModel();
        OrderStateResponseDto orderStateResponseDto = FactoryOrderDataTest.getOrderStateResponseDto();
        List<OrderStateResponseDto> orderStateResponseDtoList = new ArrayList<>();
        orderStateResponseDtoList.add(orderStateResponseDto);

        try (MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)) {
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            when(jwtHandler.extractUserName(any())).thenReturn("email@gmail.com");
            when(userClient.getByEmail(any())).thenReturn(response);
            when(restaurantEmployeeServicePort.getRestaurantByEmployeeId(anyLong())).thenReturn(restaurantEmployeeModel);
            when(restaurantServicePort.getAllRestaurants()).thenReturn(List.of(restaurantModel));
            when(orderDishServicePort.getAllOrderDish()).thenReturn(List.of(orderDishModel));
            when(orderServicePort.getAllOrdersByOrderState(orderState, 1L)).thenReturn(List.of(orderModel));
            when(orderResponseMapper.toResponseList(any(), any(), any())).thenReturn(List.of(orderStateResponseDto));

            Assertions.assertEquals(orderStateResponseDtoList, orderHandler.getAllOrdersByOrderState(orderState));
        }
    }
}