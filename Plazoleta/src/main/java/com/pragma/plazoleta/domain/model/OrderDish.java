package com.pragma.plazoleta.domain.model;

import com.pragma.plazoleta.common.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDish {
    private Long id;
    private Order orderId;
    private Dish dishId;
    private Integer quantity;

}
