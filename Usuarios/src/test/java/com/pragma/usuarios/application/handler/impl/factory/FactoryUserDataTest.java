package com.pragma.usuarios.application.handler.impl.factory;

import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.ResponseDto;
import com.pragma.usuarios.application.dto.response.RoleDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.Role;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FactoryUserDataTest {
    public static UserRequestDto getUserRequestDto(Long rolId) {
        UserRequestDto userRequestDto = new UserRequestDto();

        userRequestDto.setName("Juan Camilo");
        userRequestDto.setLastname("Gomez");
        userRequestDto.setDocumentNumber("1193078576");
        userRequestDto.setPhone("+573148022302");
        userRequestDto.setEmail("amraga10@gmail.com");
        userRequestDto.setPassword("1234");
        userRequestDto.setRoleId(rolId);

        return userRequestDto;
    }

    public static UserResponseDto getUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setName("Juan Camilo");
        userResponseDto.setLastname("Gomez");
        userResponseDto.setDocumentNumber("1193078576");
        userResponseDto.setPhone("+573148022302");
        userResponseDto.setEmail("amraga10@gmail.com");
        userResponseDto.setRoleId(getRolResponseDto());

        return userResponseDto;
    }

    public static User getUserModel(Long roleId, String rolName) {
        User userModel = new User();

        userModel.setId(1L);
        userModel.setName("Juan Camilo");
        userModel.setLastname("Gomez");
        userModel.setDocumentNumber("1193078576");
        userModel.setPhone("+573148022302");
        userModel.setEmail("amraga10@gmail.com");
        userModel.setPassword("1234");
        userModel.setRoleId(getRolModel(roleId, rolName));

        return userModel;
    }

    public static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setName("Juan Camilo");
        userEntity.setLastname("Gomez");
        userEntity.setDocumentNumber("1193078576");
        userEntity.setPhone("+573148022302");
        userEntity.setEmail("amraga10@gmail.com");
        userEntity.setPassword("1234");
        userEntity.setRoleId(getRolEntity());

        return userEntity;
    }

    public static UserEntity getUserEntity2() {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setName("Juan Camilo");
        userEntity.setLastname("Gomez");
        userEntity.setDocumentNumber("1193078576");
        userEntity.setPhone("+573148022302");
        userEntity.setEmail("amraga10@gmail.com");
        userEntity.setPassword("1234");
        userEntity.setRoleId(userEntity.getRoleId());

        return userEntity;
    }

    public static Role getRolModel(Long rolId, String roleName) {
        Role rolModel = new Role(rolId,roleName, "description");

        rolModel.setId(rolId);
        rolModel.setName(roleName);
        rolModel.setDescription("descripcion");

        return rolModel;
    }

    public static RoleEntity getRolEntity() {
        RoleEntity rolEntity = new RoleEntity();

        rolEntity.setId(1L);
        rolEntity.setName("ROLE_ADMINISTRADOR");
        rolEntity.setDescription("Administrador");

        return rolEntity;
    }

    public static RoleDto getRolResponseDto() {
        RoleDto rolResponseDto = new RoleDto();

        rolResponseDto.setName("ROLE_ADMINISTRADOR");
        rolResponseDto.setDescription("Administrador");

        return rolResponseDto;
    }



     public static ResponseDto getResponseClientDto() {
     ResponseDto responseClientDto = new ResponseDto();

     responseClientDto.setMessage("");
     responseClientDto.setError(false);
     responseClientDto.setData(getUserRequestDto());

     return responseClientDto;
     }

     public static ResponseEntity<ResponseDto> getResponseEntity() {
     ResponseDto responseClientDto = getResponseClientDto();
     return new ResponseEntity<>(responseClientDto, HttpStatus.FOUND);
     }

    public static UserResponseDto getUserRequestDto() {
        UserResponseDto userRequestDto = new UserResponseDto();

        userRequestDto.setName("Juan Camilo");
        userRequestDto.setLastname("Gomez");
        userRequestDto.setDocumentNumber("1193078576");
        userRequestDto.setPhone("+573148022302");
        userRequestDto.setEmail("amraga10@gmail.com");

        return userRequestDto;
    }

    public static RegisterRequestDto getRegisterRequestDto() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();

        registerRequestDto.setName("Juan Camilo");
        registerRequestDto.setLastname("Gomez");
        registerRequestDto.setDocumentNumber("1193078576");
        registerRequestDto.setPhone("+573148022302");
        registerRequestDto.setEmail("amraga10@gmail.com");
        registerRequestDto.setPassword("1234");

        return registerRequestDto;
    }

}