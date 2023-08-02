package com.pragma.plazoleta.application.handler.impl.factory;


import com.pragma.plazoleta.application.dto.request.OrderDishRequestDto;
import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderStateResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.domain.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FactoryOrderDataTest {

    public static OrderRequestDto getOrderRequestDto() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        List<OrderDishRequestDto> orderDishRequestDtoList = new ArrayList<>();
        orderDishRequestDtoList.add(getOrderDishRequestDto());
        orderDishRequestDtoList.add(getOrderDishRequestDto());

        orderRequestDto.setRestaurantId(1L);
        orderRequestDto.setDishes(orderDishRequestDtoList);

        return orderRequestDto;
    }

    public static OrderDishRequestDto getOrderDishRequestDto(){
        OrderDishRequestDto orderDishRequestDto = new OrderDishRequestDto();
        orderDishRequestDto.setDishId(1L);
        orderDishRequestDto.setQuantity(50);

        return orderDishRequestDto;
    }

    public static Restaurant getRestaurantModel(){
        Restaurant restaurantModel = new Restaurant();
        restaurantModel.setName("Frisby");
        restaurantModel.setAddress("Avenida Santander");
        restaurantModel.setOwnerId(3L);
        restaurantModel.setPhone("+573148022302");
        restaurantModel.setUrlLogo("google.com");
        restaurantModel.setNit("123478525");

        return restaurantModel;
    }

    public static User getUserModel() {
        User userModel = new User();

        userModel.setId(1L);
        userModel.setName("Juan Sebastian");
        userModel.setLastname("Giraldo");
        userModel.setDocumentNumber("1193078576");
        userModel.setPhone("+573148022302");
        userModel.setEmail("sebasgiraldov@gmail.com");
        userModel.setPassword("1234");
        userModel.setRoleId(null);

        return userModel;
    }

    public static Role getRolModelClient() {
        Role rolModel = new Role();
        rolModel.setId(4L);
        rolModel.setName("ROLE_CLIENTE");
        rolModel.setDescription("Cliente");
        return rolModel;
    }

    public static Order getOrderModel() {
        Order orderModel = new Order();
        orderModel.setId(1L);
        orderModel.setClientId(getUserModel());
        orderModel.setDate(new Date());
        orderModel.setStatus(OrderState.PENDIENTE);
        orderModel.setChefId(null);
        orderModel.setRestaurantId(getRestaurantModel());
        return orderModel;
    }

    public static OrderResponseDto getOrderResponseDto() {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        List<OrderDishResponseDto> orderDishResponseDtoList = new ArrayList<>();
        orderDishResponseDtoList.add(getOrderDishResponseDto());
        orderDishResponseDtoList.add(getOrderDishResponseDto());

        orderResponseDto.setOrderId(1L);
        orderResponseDto.setDate(new Date());
        orderResponseDto.setStatus(OrderState.PENDIENTE);
        orderResponseDto.setRestaurantId(getRestaurantResponseDto());
        orderResponseDto.setOrders(orderDishResponseDtoList);

        return orderResponseDto;
    }

    public static RestaurantResponseDto getRestaurantResponseDto() {
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

        restaurantResponseDto.setName("Frisby");
        restaurantResponseDto.setAddress("Avenida Santander");
        restaurantResponseDto.setOwnerId(3L);
        restaurantResponseDto.setPhone("+573148022302");
        restaurantResponseDto.setUrlLogo("logoUrl");
        restaurantResponseDto.setNit("20000");

        return restaurantResponseDto;
    }

    public static OrderDishResponseDto getOrderDishResponseDto() {
        OrderDishResponseDto orderDishResponseDto = new OrderDishResponseDto();


        return orderDishResponseDto;
    }

    public static OrderDish getOrderDishModel() {
        OrderDish orderDishModel = new OrderDish();

        orderDishModel.setId(1L);
        orderDishModel.setQuantity(2);

        return orderDishModel;
    }

    public static OrderDishResponseDto orderDishResponseDto() {
        OrderDishResponseDto orderDishResponseDto = new OrderDishResponseDto();
        return orderDishResponseDto;
    }

    public static OrderStateResponseDto getOrderStateResponseDto() {
        OrderStateResponseDto orderStateResponseDto = new OrderStateResponseDto();

        orderStateResponseDto.setOrderState(OrderState.PENDIENTE);
        orderStateResponseDto.setOrderId(1L);
        orderStateResponseDto.setDate(new Date());
        orderStateResponseDto.setOrderDishIds(List.of(1L, 2L));

        return orderStateResponseDto;
    }


}