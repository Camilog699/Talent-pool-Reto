package com.pragma.plazoleta.infrastructure.out.jpa.repository;

import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.status = :orderState AND o.restaurantId.id = :restaurantId")
    List<OrderEntity> findAllByOrderState(OrderState orderState, Long restaurantId);

}