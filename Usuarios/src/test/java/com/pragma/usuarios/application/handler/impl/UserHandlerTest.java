package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.impl.factory.FactoryUserDataTest;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.application.mapper.IUserResponseMapper;
import com.pragma.usuarios.application.mapper.RoleDtoMapper;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.exception.EmailAlreadyExistsException;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class UserHandlerTest {

    @InjectMocks
    UserHandler userHandler;

    @Mock
    IUserServicePort userServicePort;
    @Mock
    IUserRequestMapper userRequestMapper;
    @Mock
    IUserResponseMapper userResponseMapper;
    @Mock
    IRoleServicePort rolServicePort;
    @Mock
    RoleDtoMapper rolResponseMapper;

    @Test
    void mustRegisterAnUser() {
        UserRequestDto userRequestDto = FactoryUserDataTest.getUserRequestDto(1L);
        UserResponseDto userResponseDto = FactoryUserDataTest.getUserResponseDto();
        User expectedUser = FactoryUserDataTest.getUserModel(1L, "ROLE_ADMINISTRADOR");
        RoleDto rolResponseDto = FactoryUserDataTest.getRolResponseDto();

        Mockito.when(userServicePort.findUserByEmail(any())).thenReturn(Optional.empty());
        Mockito.when(rolServicePort.getRole(any())).thenReturn(expectedUser.getIdRole());
        Mockito.when(userRequestMapper.toUser(any())).thenReturn(expectedUser);
        Mockito.when(rolResponseMapper.toDto(any())).thenReturn(rolResponseDto);
        Mockito.when(userResponseMapper.toResponse(any(), any())).thenReturn(userResponseDto);

        Assertions.assertEquals(userResponseDto, userHandler.register(userRequestDto));

        Mockito.verify(userServicePort).saveUser(any(User.class));
    }

    @Test
    void throwEmailAlreadyTakenExceptionWhenAttemptToRegisterAnUser() {

        UserRequestDto userRequestDto = FactoryUserDataTest.getUserRequestDto(1L);
        UserEntity userEntity = FactoryUserDataTest.getUserEntity();

        Mockito.when(userServicePort.findUserByEmail(any())).thenReturn(Optional.of(userEntity));

        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> userHandler.register(userRequestDto)
        );
    }
}
