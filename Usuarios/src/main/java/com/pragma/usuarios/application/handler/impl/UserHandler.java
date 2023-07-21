package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.application.mapper.IUserResponseMapper;
import com.pragma.usuarios.application.mapper.RoleDtoMapper;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.Role;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final RoleDtoMapper roleDtoMapper;

    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        if( userServicePort.findUserByEmail(userRequestDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        Role role = roleServicePort.getRole(userRequestDto.getRoleId());
        User user = userRequestMapper.toUser(userRequestDto);
        user.setRoleId(role);
        User userPort = userServicePort.saveUser(user);
        RoleDto roleDto = roleDtoMapper.toDto(role);
        return userResponseMapper.toResponse(userPort, roleDto);
    }

    @Override
    public UserResponseDto getById(Long userId) {
        User user = userServicePort.getById(userId);
        return userRequestMapper.toDto(user);
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        User user = userServicePort.findUserByEmailModel(email);
        return userRequestMapper.toDto(user);
    }
}