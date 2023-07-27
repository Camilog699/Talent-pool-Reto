package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.infrastructure.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;


    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
        return restaurantEntityMapper.toObjectModel(restaurantEntity);
    }

    @Override
    public Restaurant getById(Long restaurantId) {
        return restaurantEntityMapper.toObjectModel(restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new));
    }

    @Override
    public List<Restaurant> getAllRestaurants(int pageN, int size) {
        Pageable paging = PageRequest.of(pageN, size, Sort.by("name"));
        Page<RestaurantEntity> pagedResult = restaurantRepository.findAll(paging);
        List<RestaurantEntity> restaurantEntityList = pagedResult.getContent();
        if (restaurantEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toObjectModelList(restaurantEntityList);
    }
}