package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.handler.IDishHandler;
import com.pragma.plazoleta.application.mapper.ICategoryResponseMapper;
import com.pragma.plazoleta.application.mapper.IDishRequestMapper;
import com.pragma.plazoleta.application.mapper.IDishResponseMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.configuration.FeignClientInterceptorImp;
import com.pragma.plazoleta.common.exception.CategoryNotFoundException;
import com.pragma.plazoleta.common.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final ICategoryServicePort categoryServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    private final ICategoryResponseMapper categoryResponseMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IUserFeignClient userClient;
    private final JwtHandler jwtHandler;

    @Override
    public DishResponseDto saveDish(DishRequestDto dishRequestDto) {
        Restaurant restaurant = restaurantServicePort.getById(dishRequestDto.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] split = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(split[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        if(!restaurant.getOwnerId().equals(userRequestDto.getId())){
            throw new NotEnoughPrivilegesException();
        }
        Dish dish = dishRequestMapper.toDish(dishRequestDto);

        Category category = categoryServicePort.getById(dishRequestDto.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        dish.setActive(true);
        dish.setRestaurantId(restaurant);
        dish.setCategoryId(category);
        dishServicePort.saveDish(dish);
        return dishResponseMapper.toResponse(dish, categoryResponseMapper.toResponse(category), restaurantResponseMapper.toResponse(restaurant));
    }

    @Override
    public DishResponseDto getDishById(Long id) {
        return dishResponseMapper.toResponse(dishServicePort.getById(id), categoryResponseMapper.toResponse(categoryServicePort.getById(id)), restaurantResponseMapper.toResponse(restaurantServicePort.getById(id)));
    }

    @Override
    public List<DishResponseDto> getAllDishes() {
        return dishResponseMapper.toResponseList(dishServicePort.getAllDishes(), categoryServicePort.getAllCategories(), restaurantServicePort.getAllRestaurants());
    }

    @Override
    public DishResponseDto updateDish(Long dishId, DishUpdateRequestDto dishUpdateRequestDto){
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] split = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(split[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        Dish dish = dishServicePort.getById(dishId);
        Restaurant restaurant = restaurantServicePort.getById(dish.getRestaurantId().getId());

        if (!Objects.equals(restaurant.getOwnerId(), userRequestDto.getId())) {
            throw new NotEnoughPrivilegesException();
        }

        dish.setPrice(dishUpdateRequestDto.getPrice());
        dish.setDescription(dishUpdateRequestDto.getDescription());
        dishServicePort.updateDish(dish);
        return dishResponseMapper.toResponse(dish, categoryResponseMapper.toResponse(categoryServicePort.getById(dish.getCategoryId().getId())), restaurantResponseMapper.toResponse(restaurantServicePort.getById(dish.getRestaurantId().getId())));
    }

    @Override
    public DishResponseDto enableDish(Long id){
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] split = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(split[1]);
        UserRequestDto userRequestDto = Objects.requireNonNull(userClient.getByEmail(email).getBody()).getData();
        Dish dish = dishServicePort.getById(id);
        Restaurant restaurant = restaurantServicePort.getById(dish.getRestaurantId().getId());
        if(!restaurant.getOwnerId().equals(userRequestDto.getId())){
            throw new NotEnoughPrivilegesException();
        }
        dish.setActive(!dish.getActive());
        dishServicePort.updateDish(dish);
        return dishResponseMapper.toResponse(dish, categoryResponseMapper.toResponse(categoryServicePort.getById(dish.getCategoryId().getId())), restaurantResponseMapper.toResponse(restaurantServicePort.getById(dish.getRestaurantId().getId())));
    }

    @Override
    public List<DishResponseDto> getDishByRestaurantId(ListPaginationRequest listPaginationRequest, Long id ) {
        return dishResponseMapper.toResponseList(dishServicePort.getDishByRestaurantId(listPaginationRequest.getPageN(),
                listPaginationRequest.getSize(), id),
                categoryServicePort.getAllCategories(),
                restaurantServicePort.getAllRestaurants());
    }
}