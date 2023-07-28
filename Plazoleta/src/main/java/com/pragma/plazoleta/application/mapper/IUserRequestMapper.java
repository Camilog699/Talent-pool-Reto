package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    User toUser(UserRequestDto userRequestDto);
}
