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
public class Order {
    private Long id;
    private User clientId;
    private Date date;
    private OrderState status;
    private User chefId;
    private Restaurant restaurantId;

}
