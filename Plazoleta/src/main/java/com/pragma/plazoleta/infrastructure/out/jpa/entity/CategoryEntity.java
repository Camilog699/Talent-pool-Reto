package com.pragma.plazoleta.infrastructure.out.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id", nullable = false)
    private Long id;
    private String name;
    private String description;
}