package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.AllRestaurantResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.application.mapper.IRestaurantRequestMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta.common.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.common.exception.OwnerIdNotFoundException;
import com.pragma.plazoleta.infrastructure.configuration.FeignClientInterceptorImp;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IUserFeignClient userFeignClient;
    private final JwtHandler jwtHandler;
    @Override
    public RestaurantResponseDto saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] split = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(split[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userFeignClient.getByEmail(email).getBody()).getData();
        if(!Objects.equals(userRequestDto.getRoleId().getName(), "ROLE_ADMINISTRADOR")){
            throw new NotEnoughPrivilegesException();
        }

        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
        Object user = userFeignClient.getUserById(restaurant.getOwnerId());
        if (user == null) {
            throw new OwnerIdNotFoundException();
        }
        restaurant.setOwnerId(restaurantRequestDto.getOwnerId());
        return restaurantResponseMapper.toResponse(restaurantServicePort.saveRestaurant(restaurant));
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        return restaurantResponseMapper.toResponse(restaurantServicePort.getById(id));
    }

    @Override
    public List<AllRestaurantResponseDto> getAllRestaurants(ListPaginationRequest listPaginationRequest) {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurants( listPaginationRequest.getPageN(), listPaginationRequest.getSize()));
    }
}