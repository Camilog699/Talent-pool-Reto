package com.pragma.usuarios.domain.spi;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import java.util.Optional;

public interface IUserPersistencePort {
    User saveUser(User user);

    Optional<UserEntity> findUserByEmail(String email);
    User findUserByEmailModel(String email);
    User getById(Long userId);
}