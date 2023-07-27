package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.ResponseDto;
import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IJwtHandler;
import com.pragma.usuarios.application.handler.impl.factory.FactoryUserDataTest;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.application.mapper.IUserResponseMapper;
import com.pragma.usuarios.application.mapper.RoleDtoMapper;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.configuration.FeignClientInterceptorImp;
import com.pragma.usuarios.infrastructure.exception.EmailAlreadyExistsException;
import com.pragma.usuarios.infrastructure.input.rest.Plazoleta.IPlazoletaFeignClient;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
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
    @Mock
    IJwtHandler jwtHandler;

    @Mock
    IPlazoletaFeignClient plazoletaService;
    public static final String ROLE_ADMINISTRADOR = "ROLE_ADMINISTRADOR";
    public static final String ROLE_EMPLEADO = "ROLE_EMPLEADO";

    @Test
    void mustRegisterAnUser() {
        UserRequestDto userRequestDto = FactoryUserDataTest.getUserRequestDto(1L);
        UserResponseDto userResponseDto = FactoryUserDataTest.getUserResponseDto();
        User expectedUser = FactoryUserDataTest.getUserModel(1L, ROLE_ADMINISTRADOR);
        RoleDto rolResponseDto = FactoryUserDataTest.getRolResponseDto();

        Mockito.when(userServicePort.findUserByEmail(any())).thenReturn(Optional.empty());
        Mockito.when(rolServicePort.getRole(any())).thenReturn(expectedUser.getRoleId());
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

    @Test
    void mustRegisterAnEmployee(){
        RegisterRequestDto registerRequestDto = FactoryUserDataTest.getRegisterRequestDto();
        UserRequestDto userRequestDto = FactoryUserDataTest.getUserRequestDto(3L);
        User userModel = FactoryUserDataTest.getUserModel(3L, ROLE_EMPLEADO);
        UserResponseDto userResponseDto = FactoryUserDataTest.getUserResponseDto();
        ResponseEntity<ResponseDto> response = FactoryUserDataTest.getResponseEntity();
        UserEntity userEntity2 = FactoryUserDataTest.getUserEntity2();

        try(MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)){
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            Mockito.when(userServicePort.findUserByEmail(registerRequestDto.getEmail())).thenReturn(Optional.empty());
            Mockito.when(jwtHandler.extractUserName(any())).thenReturn("sebasgiraldov2@gmail.com");
            Mockito.when(userServicePort.findUserByEmail("sebasgiraldov2@gmail.com")).thenReturn(Optional.of(userEntity2));
            Mockito.when(userServicePort.saveUser(any())).thenReturn(userModel);
            Mockito.when(userRequestMapper.toUserRequestDto(any())).thenReturn(userRequestDto);
            Mockito.when(userRequestMapper.toUser(any())).thenReturn(userModel);
            Mockito.when(rolServicePort.getRole(any())).thenReturn(userModel.getRoleId());
            Mockito.when(userResponseMapper.toResponse(any(),any())).thenReturn(userResponseDto);
            Mockito.when(plazoletaService.saveRestaurantEmployee(any())).thenReturn(response);

            Assertions.assertEquals(userResponseDto, userHandler.registerEmployee(registerRequestDto, 3L));

            Mockito.verify(userServicePort).saveUser(any(User.class));
        }
    }

    @Test
    void throwEmailAlreadyTakenExceptionWhenAttemptToRegisterAnEmployee(){
        RegisterRequestDto registerRequestDto = FactoryUserDataTest.getRegisterRequestDto();
        UserEntity userEntity = FactoryUserDataTest.getUserEntity();

        Mockito.when(userServicePort.findUserByEmail(any())).thenReturn(Optional.of(userEntity));

        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> userHandler.registerEmployee(registerRequestDto, 3L)
        );
    }

/**
    @Test
    void throwNoDataFoundExceptionWhenAttempToRegisterAnEmployee(){
        RegisterRequestDto registerRequestDto = FactoryUserDataTest.getRegisterRequestDto();
        UserRequestDto userRequestDto = FactoryUserDataTest.getUserRequestDto(3L);
        User userModel = FactoryUserDataTest.getUserModel(3l, "ROLE_EMPLEADO");
        UserResponseDto userResponseDto = FactoryUserDataTest.getUserResponseDto();
        ResponseEntity<ResponseDto> response = FactoryUserDataTest.getResponseEntity();
        UserEntity userEntity2 = FactoryUserDataTest.getUserEntity2();

        try(MockedStatic<FeignClientInterceptorImp> utilities = Mockito.mockStatic(FeignClientInterceptorImp.class)){
            utilities.when(FeignClientInterceptorImp::getBearerTokenHeader).thenReturn("Bearer token");
            Mockito.when(userServicePort.findUserByEmail(registerRequestDto.getEmail())).thenReturn(Optional.empty());
            Mockito.when(jwtHandler.extractUserName(any())).thenReturn("sebasgiraldov2@gmail.com");
            Mockito.when(userServicePort.findUserByEmail("sebasgiraldov2@gmail.com")).thenReturn(Optional.empty());

            Assertions.assertThrows(
                    NoDataFoundException.class,
                    () -> userHandler.registerEmployee(registerRequestDto, 3L)
            );
        }
    }
    */
}