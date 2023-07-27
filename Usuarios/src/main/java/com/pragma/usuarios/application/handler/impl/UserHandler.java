package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.AuthenticationRequestDto;
import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.JwtResponseDto;
import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IJwtHandler;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.application.mapper.IUserResponseMapper;
import com.pragma.usuarios.application.mapper.RoleDtoMapper;
import com.pragma.usuarios.domain.api.IRoleServicePort;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.Role;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.configuration.FeignClientInterceptorImp;
import com.pragma.usuarios.infrastructure.exception.EmailAlreadyExistsException;
import com.pragma.usuarios.infrastructure.input.rest.Plazoleta.IPlazoletaFeignClient;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final RoleDtoMapper roleDtoMapper;

    private final AuthenticationManager authenticationManager;
    private final IJwtHandler jwtHandler;
    private final IPlazoletaFeignClient plazoletaFeignClient;

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
    public JwtResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        JwtResponseDto jwtResponseDto = new JwtResponseDto();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        var user = userServicePort.findUserByEmail(authenticationRequestDto.getEmail()).orElseThrow();
        var jwtToken = jwtHandler.generateToken(user);

        jwtResponseDto.setToken(jwtToken);
        jwtResponseDto.setBearer(userEntity.getEmail());
        jwtResponseDto.setUserName(userEntity.getName());
        jwtResponseDto.setAuthorities(userEntity.getAuthorities());

        return jwtResponseDto;
    }

    @Override
    public UserResponseDto registerOwner(UserRequestDto userRequestDto) {
        if( userServicePort.findUserByEmail(userRequestDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        Role role = roleServicePort.getRole(2L);
        User user = userRequestMapper.toUser(userRequestDto);
        user.setRoleId(role);
        User userPort = userServicePort.saveUser(user);
        RoleDto roleDto = roleDtoMapper.toDto(role);
        return userResponseMapper.toResponse(userPort, roleDto);
    }

    public UserResponseDto registerEmployee(RegisterRequestDto registerRequestDto, Long restaurantId) {
        if( userServicePort.findUserByEmail(registerRequestDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        String tokenHeader = FeignClientInterceptorImp.getBearerTokenHeader();
        String[] split = tokenHeader.split("\\s+");
        String email = jwtHandler.extractUserName(split[1]);

        Optional<UserEntity> userEntityOptional = userServicePort.findUserByEmail(email);

        if(userEntityOptional.isEmpty()){
            throw new NotFoundException("User not found");
        }
        UserEntity owner = userEntityOptional.get();

        UserRequestDto userRequestDto = userRequestMapper.toUserRequestDto(registerRequestDto);

        Role role = roleServicePort.getRole(3L);
        User user = userRequestMapper.toUser(userRequestDto);
        user.setRoleId(role);
        User userPort = userServicePort.saveUser(user);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setRestaurantId(restaurantId);
        employeeRequestDto.setEmployeeId(userPort.getId());
        employeeRequestDto.setOwnerId(owner.getId());
        plazoletaFeignClient.saveRestaurantEmployee(employeeRequestDto);

        return userResponseMapper.toResponse(userPort, roleDtoMapper.toDto(role));
    }

    @Override
    public UserResponseDto registerClient(RegisterRequestDto registerRequestDto) {
        if( userServicePort.findUserByEmail(registerRequestDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        Role role = roleServicePort.getRole(4L);
        UserRequestDto userRequestDto = userRequestMapper.toUserRequestDto(registerRequestDto);
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