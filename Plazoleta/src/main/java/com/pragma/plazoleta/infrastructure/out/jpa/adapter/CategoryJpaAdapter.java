package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.ICategoryPersistencePort;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.CategoryNotFoundException;
import com.pragma.plazoleta.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.infrastructure.exception.RestaurantNotFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public Category getById(Long categoryId) {
        return categoryEntityMapper.toCategoryModel(categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new));
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryModelList(entityList);
    }
}