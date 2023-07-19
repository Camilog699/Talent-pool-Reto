package com.pragma.usuarios.infrastructure.out.jpa.entity;

import com.pragma.usuarios.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;
    private String name;
    private String lastName;
    private String documentNumber;
    private String phone;
    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RoleEntity role;
}
