package com.pragma.plazoleta.infrastructure.out.jpa.entity;

import lombok.*;

import javax.persistence.*;
@Data
@Builder
@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dish_id", nullable = false)
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String urlImage;
    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryId;
    @OneToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurantId;
    private Boolean active;
}