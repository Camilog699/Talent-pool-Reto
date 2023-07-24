package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    Category getById(Long categoryId);

    List<Category> getAllCategories();
}