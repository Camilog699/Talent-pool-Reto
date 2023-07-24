package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {


    Category getById(Long categoryId);

    List<Category> getAllCategories();
}