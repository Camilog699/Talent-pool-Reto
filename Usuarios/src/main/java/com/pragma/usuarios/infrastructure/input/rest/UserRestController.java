package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> register(@RequestBody UserRequestDto userRequestDto) {
        userHandler.register(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    @GetMapping("/getUser/{idUser}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long idUser) {
        UserResponseDto user = userHandler.getById(idUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}