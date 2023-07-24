package com.pragma.usuarios.application.mapper;

import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {RoleDtoMapper.class})
public interface IUserResponseMapper {

    RoleDtoMapper INSTANCE = Mappers.getMapper(RoleDtoMapper.class);
    @Mapping(target = "roleId.name", source = "roleDto.name")
    @Mapping(target = "roleId.description", source = "roleDto.description")
    @Mapping(target = "name", source = "user.name")
    UserResponseDto toResponse(User user, RoleDto roleDto);


}
