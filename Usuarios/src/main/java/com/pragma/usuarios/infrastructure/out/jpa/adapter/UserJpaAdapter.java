package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.exception.NoDataFoundException;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity userModel = userRepository.save(userEntity);
        return userEntityMapper.toUser(userModel);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmailModel(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        UserEntity userEntity = userEntityOptional.get();
        User user = userEntityMapper.toUser(userEntity);
        return user;
    }

    @Override
    public User getById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(NoDataFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

}