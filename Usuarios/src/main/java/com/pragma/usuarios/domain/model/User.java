package com.pragma.usuarios.domain.model;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String phone;
    private Date dateBirth;
    private String email;
    private String password;
    private Role roleId;

    /**
    public User(Long id, String name, String lastname, String documentNumber, String phone, Date dateBirth, String email, String password, Role roleId) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.dateBirth = dateBirth;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getIdRole() {
        return roleId;
    }

    public void setIdRole(Role roleId) {
        this.roleId = roleId;
    }
     */
}
