package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserServicePort {

    User saveUser(User userModel);
    Optional<UserEntity> findUserByEmail(String email);

    User findUserByEmailModel(String email);

    User getById(Long userId);
}