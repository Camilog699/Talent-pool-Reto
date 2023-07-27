package com.pragma.plazoleta.infrastructure.out.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "restaurant_employee_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurantId;
    private Long employeeId;
    private String field;
}