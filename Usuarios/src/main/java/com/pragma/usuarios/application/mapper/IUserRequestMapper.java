package com.pragma.usuarios.application.mapper;

import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    @Mapping(source = "userRequestDto.roleId", target = "roleId.id")
    User toUser(UserRequestDto userRequestDto);

    UserRequestDto toUserRequestDto(RegisterRequestDto registerRequestDto);
    UserResponseDto toDto(User user);

}
