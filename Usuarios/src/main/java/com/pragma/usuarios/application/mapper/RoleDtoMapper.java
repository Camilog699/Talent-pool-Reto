package com.pragma.usuarios.application.mapper;

import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleDtoMapper {
    RoleDto toDto(Role role);

}
