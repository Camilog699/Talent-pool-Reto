package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.AuthenticationRequestDto;
import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.JwtResponseDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;

public interface IUserHandler {

    UserResponseDto register(UserRequestDto userRequestDto);

      JwtResponseDto login(AuthenticationRequestDto authenticationRequestDto);

    UserResponseDto registerOwner(RegisterRequestDto registerRequestDto);

    UserResponseDto registerEmployee(RegisterRequestDto registerRequestDto, Long restaurantId);

    UserResponseDto registerClient(RegisterRequestDto registerRequestDto);

    UserResponseDto getById(Long userId);

    UserResponseDto getByEmail(String email);

}