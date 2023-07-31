package com.pragma.plazoleta.infrastructure.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.Order;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {

    @Mapping(source = "order.clientId.id", target = "clientId")
    @Mapping(source = "order.chefId.id", target = "chefId")
    OrderEntity toEntity(Order order);

    @Mapping(source = "clientId", target = "clientId.id")
    @Mapping(source = "chefId", target = "chefId.id")
    Order toObjectModel(OrderEntity orderEntity);
    List<Order> toObjectModelList(List<OrderEntity> orderEntityList);
}