package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.common.OrderState;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private Date date;
    private OrderState status;
    private RestaurantResponseDto restaurantId;
    private List<OrderDishResponseDto> orders;
}
