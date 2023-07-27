package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.ResponseDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.infrastructure.exception.EmailAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final IUserHandler userHandler;

    @Operation(summary = "Register a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Email already taken", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid register request", content = @Content),
            @ApiResponse(responseCode = "403", description = "User with not enough privileges", content = @Content)
    })
    @RolesAllowed({"ROLE_ADMINISTRADOR"})
    @PostMapping("/owner")
    public ResponseEntity<ResponseDto> ownerRegister(@Valid @RequestBody UserRequestDto userRequestDto,
                                                     BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            UserResponseDto userResponseDto = userHandler.registerOwner(userRequestDto);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(userResponseDto);
        } catch (EmailAlreadyExistsException exception) {
            responseDto.setError(true);
            responseDto.setMessage("El email ingresado ya está en uso");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            responseDto.setError(true);
            responseDto.setMessage("Error interno del servidor");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @Operation(summary = "Register a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Email already taken",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))),
            @ApiResponse(responseCode = "403", description = "User with not enough privileges", content = @Content)
    })
    @RolesAllowed({"ROLE_PROPIETARIO"})
    @PostMapping("/employee/{restaurantId}")
    public ResponseEntity<ResponseDto> employeeRegister(@Valid @RequestBody RegisterRequestDto registerRequestDto,
                                                        @PathVariable Long restaurantId,
                                                        BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            UserResponseDto userResponseDto = userHandler.registerEmployee(registerRequestDto, restaurantId);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(userResponseDto);
        } catch (EmailAlreadyExistsException exception) {
            responseDto.setError(true);
            responseDto.setMessage("El email ingresado ya está en uso");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            responseDto.setError(true);
            responseDto.setMessage("Error interno del servidor"+ exception);
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    private ResponseEntity<ResponseDto> ValidationErrors(BindingResult bindingResult, ResponseDto responseDto) {
        List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

        responseDto.setError(true);
        responseDto.setMessage("Error en las validaciones");
        responseDto.setData(errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}