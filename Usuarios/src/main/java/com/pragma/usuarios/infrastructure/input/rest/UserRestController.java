package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.AuthenticationRequestDto;
import com.pragma.usuarios.application.dto.request.RegisterRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.JwtResponseDto;
import com.pragma.usuarios.application.dto.response.ResponseDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.infrastructure.exception.EmailAlreadyExistsException;
import com.pragma.usuarios.infrastructure.exception.NoDataFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            UserResponseDto userResponseDto = userHandler.register(userRequestDto);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(userResponseDto);
        } catch (EmailAlreadyExistsException exception) {
            responseDto.setError(true);
            responseDto.setMessage("Email already exists");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Register new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Client already exists", content = @Content)
    })
    @PostMapping("/client")
    public ResponseEntity<ResponseDto> registerClient(@Valid @RequestBody RegisterRequestDto registerRequestDto, BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            UserResponseDto userResponseDto = userHandler.registerClient(registerRequestDto);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(userResponseDto);
        } catch (EmailAlreadyExistsException exception) {
            responseDto.setError(true);
            responseDto.setMessage("Email already exists");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(userHandler.login(authenticationRequestDto));
    }

    @Operation(summary = "Get user by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getUserById(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto();
        Long userId = Long.parseLong(String.valueOf(id));
        try {
            userHandler.getById(userId);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(userHandler.getById(userId));
        } catch (NoDataFoundException ex) {
            responseDto.setError(true);
            responseDto.setMessage("User not found");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDto> getUserByEmail(@PathVariable String email) {
        ResponseDto responseDto = new ResponseDto();
        try {
            userHandler.getByEmail(email);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(userHandler.getByEmail(email));
        } catch (NoDataFoundException ex) {
            responseDto.setError(true);
            responseDto.setMessage("User not found");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private ResponseEntity<ResponseDto> ValidationErrors(BindingResult bindingResult, ResponseDto responseDto) {
        List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

        responseDto.setError(true);
        responseDto.setMessage("Validation errors");
        responseDto.setData(errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}