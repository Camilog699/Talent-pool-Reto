package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
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
import com.pragma.plazoleta.infrastructure.exception.CategoryNotFoundException;
import com.pragma.plazoleta.infrastructure.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.infrastructure.exception.NotUpdatableFieldsException;
import com.pragma.plazoleta.infrastructure.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Dish dish = dishRequestMapper.toDish(dishRequestDto);
        Restaurant restaurant = restaurantServicePort.getById(dishRequestDto.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        Category category = categoryServicePort.getById(dishRequestDto.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        //Esta parte de handler
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
        return dishResponseMapper.toResponseList(dishServicePort.getAllDishes());
    }

    @Override
    public DishResponseDto updateDish(Long dishId, DishRequestDto dishRequestDto){
        Dish dish = dishServicePort.getById(dishId);
        if(!dish.getName().equals(dishRequestDto.getName())
                || !dish.getCategoryId().getId().equals(dishRequestDto.getCategoryId())
                || !dish.getRestaurantId().getId().equals(dishRequestDto.getRestaurantId())
                || !dish.getActive().equals(dishRequestDto.getActive())
                || !dish.getUrlImage().equals(dishRequestDto.getUrlImage())){
            throw new NotUpdatableFieldsException();
        }
        dish.setPrice(dishRequestDto.getPrice());
        dish.setDescription(dishRequestDto.getDescription());
        dishServicePort.updateDish(dish);
        return dishResponseMapper.toResponse(dish, categoryResponseMapper.toResponse(categoryServicePort.getById(dishRequestDto.getCategoryId())), restaurantResponseMapper.toResponse(restaurantServicePort.getById(dishRequestDto.getRestaurantId())));
    }

    public DishResponseDto enableDish(Long id){
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] splited = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(splited[1]);
        UserRequestDto userRequestDto = (UserRequestDto) userClient.getByEmail(email).getBody().getData();
        Dish dish = dishServicePort.getById(id);
        Restaurant restaurant = restaurantServicePort.getById(dish.getRestaurantId().getId());
        if(!restaurant.getOwnerId().equals(userRequestDto.getId())){
            throw new NotEnoughPrivilegesException();
        }
        dish.setActive(!dish.getActive());
        dishServicePort.updateDish(dish);
        return dishResponseMapper.toResponse(dish, categoryResponseMapper.toResponse(categoryServicePort.getById(id)), restaurantResponseMapper.toResponse(restaurantServicePort.getById(id)));
    }

    @Override
    public List<DishResponseDto> getDishByRestaurantId(Long id) {
        return dishResponseMapper.toResponseList(dishServicePort.getDishByRestaurantId(id));
    }
}