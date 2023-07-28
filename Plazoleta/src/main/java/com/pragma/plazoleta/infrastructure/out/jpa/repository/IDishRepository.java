package com.pragma.plazoleta.infrastructure.out.jpa.repository;

import com.pragma.plazoleta.infrastructure.out.jpa.entity.DishEntity;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    @Query("SELECT d FROM DishEntity d WHERE d.restaurantId.id = :restaurantId")
    Page<DishEntity> findByRestaurantId(@Param("restaurantId") Long restaurantId, Pageable pageable);

    Page<DishEntity> findAll(Pageable pageable);

}