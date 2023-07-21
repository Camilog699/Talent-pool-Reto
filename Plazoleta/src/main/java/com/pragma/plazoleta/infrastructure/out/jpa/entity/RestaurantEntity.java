package com.pragma.plazoleta.infrastructure.out.jpa.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Builder
@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "restaurant_id", nullable = false)
    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String urlLogo;
    private String nit;
}