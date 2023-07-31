package com.pragma.plazoleta.infrastructure.out.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "order_dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDishEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_dish_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderId;
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dishId;
    private Integer quantity;
}