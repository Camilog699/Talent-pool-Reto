package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.User;

import java.util.List;

public interface IUserHandler {

    UserResponseDto register(UserRequestDto userRequestDto);

    UserResponseDto getById(Long userId);

    UserResponseDto getByEmail(String email);

}