package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.*;
import com.pragma.plazoleta.application.dto.response.OrderDishResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderStateResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.handler.IJwtHandler;
import com.pragma.plazoleta.application.handler.IOrderDishHandler;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import com.pragma.plazoleta.application.mapper.*;
import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.common.exception.*;
import com.pragma.plazoleta.domain.api.*;
import com.pragma.plazoleta.domain.model.*;
import com.pragma.plazoleta.infrastructure.configuration.FeignClientInterceptorImp;
import com.pragma.plazoleta.infrastructure.input.rest.client.ITwilioFeignClient;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderDishResponseMapper orderDishResponseMapper;
    private final IUserFeignClient userClient;
    private final ITwilioFeignClient twilioClient;
    private final IJwtHandler jwtHandler;
    private final IDishServicePort dishServicePort;
    private final IOrderDishHandler orderDishHandler;
    private final IUserRequestMapper userRequestMapper;
    private final IEmployeeServicePort employeeServicePort;
    private final IOrderDishServicePort orderDishServicePort;


    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Restaurant restaurant = restaurantServicePort.getById(orderRequestDto.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] split = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(split[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        List<OrderState> orderStateList = Arrays.asList(OrderState.EN_PREPARACION, OrderState.PENDIENTE, OrderState.LISTO);
        List<OrderDishRequestDto> dishesRequest = orderRequestDto.getDishes();
        Order order = new Order();
        order.setRestaurantId(restaurant);
        order.setClientId(userRequestMapper.toUser(userRequestDto));
        order.setChefId(null);
        order.setDate(new Date());
        order.setStatus(OrderState.PENDIENTE);

        Order orderResponse = orderServicePort.createOrder(order);

        List<OrderDishResponseDto> dishesResponse = dishesRequest.stream().map(orderDish ->{

                if(!Objects.equals(dishServicePort.getById(orderDish.getDishId()).getRestaurantId().getId(), orderRequestDto.getRestaurantId())){
                    throw new DishNotFoundException();
                }
                return orderDishHandler.createOrderDish(orderDish, orderResponse.getId());
                }).collect(Collectors.toList());


        return orderResponseMapper.toResponse(orderResponse, dishesResponse);
    }

    @Override
    public List<OrderStateResponseDto> getAllOrdersByOrderState(OrderState orderState) {
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String splited[] = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(splited[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();

        Employee employeeModel = employeeServicePort.getRestaurantByEmployeeId(userRequestDto.getId());
        if (employeeModel == null) {
            throw new NotEnoughPrivilegesException();
        }

        return orderResponseMapper.toResponseList(orderServicePort.getAllOrdersByOrderState(orderState, employeeModel.getRestaurantId().getId()), restaurantServicePort.getAllRestaurants(), orderDishServicePort.getAllOrderDish());
    }

    @Override
    public OrderResponseDto updateOrder(Order order, Long orderId) {
        Order orderModel = orderServicePort.getById(orderId);
        orderModel.setChefId(order.getChefId());
        orderModel.setDate(order.getDate());
        orderModel.setStatus(order.getStatus());

        orderServicePort.updateOrder(order, orderId);

        List<OrderDish> orderDishModelList = orderDishServicePort.getAllOrderDishByOrder(orderId);
        List<OrderDishResponseDto> orderDishResponseDtoList = orderDishModelList.stream().map(orderDishResponseMapper::toResponse).collect(Collectors.toList());
        return orderResponseMapper.toResponse(order, orderDishResponseDtoList);
    }

    @Override
    public OrderResponseDto assignOrderToEmployee(Long orderId){
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String splited[] = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(splited[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        Employee employeeModel = employeeServicePort.getRestaurantByEmployeeId(userRequestDto.getId());
        if (employeeModel == null) {
            throw new NotEnoughPrivilegesException();
        }
        if (!Objects.equals(orderServicePort.getById(orderId).getRestaurantId().getId(), employeeModel.getRestaurantId().getId())) {
            throw new NotEnoughPrivilegesForThisRestaurantException();
        }
        Order orderModel = orderServicePort.getById(orderId);

        orderModel.setChefId(userRequestMapper.toUser(userRequestDto));
        orderModel.setStatus(OrderState.EN_PREPARACION);
        return updateOrder(orderModel, orderId);
    }

    @Override
    public OrderResponseDto orderReady(Long orderId){
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String splited[] = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(splited[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        Employee employeeModel = employeeServicePort.getRestaurantByEmployeeId(userRequestDto.getId());
        if (employeeModel == null) {
            throw new NotEnoughPrivilegesException();
        }
        if (!Objects.equals(orderServicePort.getById(orderId).getRestaurantId().getId(), employeeModel.getRestaurantId().getId())) {
            throw new NotEnoughPrivilegesForThisRestaurantException();
        }
        Order orderModel = orderServicePort.getById(orderId);
        orderModel.setStatus(OrderState.LISTO);
        TwilioRequestDto twilioRequestDto = new TwilioRequestDto();
        twilioRequestDto.setMessage(String.valueOf(orderModel.getId()*1110));
        UserRequestDto clientRequestDto = userClient.getUserById(orderModel.getClientId().getId()).getBody().getData();
        twilioRequestDto.setNumber(clientRequestDto.getPhone());
        ResponseDto responseDto = twilioClient.sendMessage(twilioRequestDto).getBody();
        if (responseDto == null) {
            throw new NotMessageSendException();
        }
        return updateOrder(orderModel, orderId);
    }

    @Override
    public OrderResponseDto orderDelivered(Long orderId, Long pin){
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String splited[] = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(splited[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        Employee employeeModel = employeeServicePort.getRestaurantByEmployeeId(userRequestDto.getId());
        if (employeeModel == null) {
            throw new NotEnoughPrivilegesException();
        }
        if (!Objects.equals(orderServicePort.getById(orderId).getRestaurantId().getId(), employeeModel.getRestaurantId().getId())) {
            throw new NotEnoughPrivilegesForThisRestaurantException();
        }
        Order orderModel = orderServicePort.getById(orderId);
        if (!Objects.equals(orderModel.getId()*1110, pin)) {
            throw new InvalidPinException();
        }
        if (orderModel.getStatus() != OrderState.LISTO) {
            throw new OrderNotReadyException();
        }
        orderModel.setStatus(OrderState.ENTREGADO);
        return updateOrder(orderModel, orderId);
    }

    @Override
    public OrderResponseDto orderCancel(Long orderId){
        Order orderModel = orderServicePort.getById(orderId);
        if(orderModel.getStatus() != OrderState.PENDIENTE){
            throw new OrderNotPendingException();
        }
        orderModel.setStatus(OrderState.CANCELADO);
        return updateOrder(orderModel, orderId);
    }

}