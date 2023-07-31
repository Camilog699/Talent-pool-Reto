package com.pragma.plazoleta.infrastructure.out.jpa.entity;

import com.pragma.plazoleta.common.OrderState;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id", nullable = false)
    private Long id;
    private Long clientId;
    private Date date;
    private OrderState status;
    private Long chefId;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurantId;
}