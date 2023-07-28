package com.pragma.plazoleta.application.handler.impl.factory;


import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.request.RoleRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.dto.response.ResponseClientDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class FactoryRestaurantDataTest {

    public static Restaurant getRestaurantModel() {
        Restaurant restaurantModel = new Restaurant();
        restaurantModel.setName("Frisby");
        restaurantModel.setAddress("Avenida Santander");
        restaurantModel.setOwnerId(3L);
        restaurantModel.setPhone("+573148022302");
        restaurantModel.setUrlLogo("google.com");
        restaurantModel.setNit("123478525");

        return restaurantModel;
    }

    public static RestaurantRequestDto getRestaurantRequestDto() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();

        restaurantRequestDto.setName("Frisby");
        restaurantRequestDto.setAddress("Avenida Santander");
        restaurantRequestDto.setOwnerId(3L);
        restaurantRequestDto.setPhone("+573148022302");
        restaurantRequestDto.setUrlLogo("logoUrl");
        restaurantRequestDto.setNit("20000");

        return restaurantRequestDto;
    }

    public static RestaurantResponseDto getRestaurantResponseDto() {
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

        restaurantResponseDto.setName("Frisby");
        restaurantResponseDto.setAddress("Avenida Santander");
        restaurantResponseDto.setOwnerId(3L);
        restaurantResponseDto.setPhone("+573148022302");
        restaurantResponseDto.setUrlLogo("logoUrl");
        restaurantResponseDto.setNit("20000");

        return restaurantResponseDto;
    }

    public static RestaurantRequestDto getRestaurantWithoutName() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();

        restaurantRequestDto.setAddress("Avenida Santander");
        restaurantRequestDto.setOwnerId(3L);
        restaurantRequestDto.setPhone("+573148022302");
        restaurantRequestDto.setUrlLogo("logoUrl");
        restaurantRequestDto.setNit("20000");

        return restaurantRequestDto;
    }

    public static RestaurantRequestDto getRestaurantBadPhoneNumber() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();

        restaurantRequestDto.setName("Frisby");
        restaurantRequestDto.setAddress("Avenida Santander");
        restaurantRequestDto.setOwnerId(3L);
        restaurantRequestDto.setPhone("Telefono");
        restaurantRequestDto.setUrlLogo("logoUrl");
        restaurantRequestDto.setNit("20000");

        return restaurantRequestDto;
    }

    public static RestaurantRequestDto getRestaurantInvalidName() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();

        restaurantRequestDto.setName("123546");
        restaurantRequestDto.setAddress("Avenida Santander");
        restaurantRequestDto.setOwnerId(3L);
        restaurantRequestDto.setPhone("+573148022302");
        restaurantRequestDto.setUrlLogo("logoUrl");
        restaurantRequestDto.setNit("20000");

        return restaurantRequestDto;
    }

    public static Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }

    public static UserRequestDto getUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();

        userRequestDto.setId(1L);
        userRequestDto.setName("Juan Sebastian");
        userRequestDto.setLastname("Giraldo");
        userRequestDto.setDocumentNumber("1193078576");
        userRequestDto.setPhone("+573148022302");
        userRequestDto.setEmail("sebasgiraldov@gmail.com");
        userRequestDto.setPassword("1234");
        userRequestDto.setRoleId(getRoleRequestDto());

        return userRequestDto;
    }

    public static RoleRequestDto getRoleRequestDto() {
        RoleRequestDto rolRequestDto = new RoleRequestDto();
        rolRequestDto.setName("ROLE_ADMINISTRADOR");
        rolRequestDto.setDescription("Administrador");
        return rolRequestDto;
    }

    public static ResponseClientDto getResponseClientDto() {
        ResponseClientDto responseClientDto = new ResponseClientDto();

        responseClientDto.setMessage("");
        responseClientDto.setError(false);
        responseClientDto.setData(getUserRequestDto());

        return responseClientDto;
    }

    public static ResponseEntity<ResponseClientDto> getResponseEntity() {
        ResponseClientDto responseClientDto = getResponseClientDto();
        return new ResponseEntity<>(responseClientDto, HttpStatus.FOUND);
    }

    public static User getUserModel() {
        User userModel = new User();

        userModel.setId(1L);
        userModel.setName("Juan Sebastian");
        userModel.setLastname("Giraldo");
        userModel.setDocumentNumber("1193078576");
        userModel.setPhone("+573148022302");
        userModel.setEmail("sebasgiraldov@gmail.com");
        userModel.setPassword("1234");
        userModel.setRoleId(null);

        return userModel;
    }
}
