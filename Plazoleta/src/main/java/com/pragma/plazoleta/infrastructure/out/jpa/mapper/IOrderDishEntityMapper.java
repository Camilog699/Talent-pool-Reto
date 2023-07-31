package com.pragma.plazoleta.infrastructure.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.domain.model.OrderDish;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishEntityMapper {

    @Mapping(source = "orderDish.orderId.id", target = "orderId.id")
    @Mapping(source = "orderDish.orderId.clientId.id", target = "orderId.clientId")
    @Mapping(source = "orderDish.orderId.chefId.id", target = "orderId.chefId")
    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(source = "orderDishEntity.orderId.id", target = "orderId.id")
    @Mapping(source = "orderDishEntity.orderId.clientId", target = "orderId.clientId.id")
    @Mapping(source = "orderDishEntity.orderId.chefId", target = "orderId.chefId.id")
    OrderDish toObjectModel(OrderDishEntity orderDishEntity);
    List<OrderDish> toOrderDishModelList(List<OrderDishEntity> orderDishEntityList);
}