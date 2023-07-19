package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import java.util.Optional;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User saveUser(User user) {
        return userPersistencePort.saveUser(user);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userPersistencePort.findUserByEmail(email);
    }

    @Override
    public User findUserByEmailModel(String email) {
        return userPersistencePort.findUserByEmailModel(email);
    }

    @Override
    public User getById(Long userId) {
        return userPersistencePort.getById(userId);
    }
}