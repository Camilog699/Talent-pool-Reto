package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.AllRestaurantResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.infrastructure.exception.NotEnoughPrivilegesException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Save new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Restaurant already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<ResponseDto> saveRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto, BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            RestaurantResponseDto restaurantResponseDto = restaurantHandler.saveRestaurant(restaurantRequestDto);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(restaurantResponseDto );
        } catch (NotEnoughPrivilegesException ex) {
            responseDto.setError(true);
            responseDto.setMessage("el usuario debe ser propietario");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            responseDto.setError(true);
            responseDto.setMessage("No se encontraron datos de usuario");
            responseDto.setData(ex);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All restaurants returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getAllRestaurants(@Valid @RequestBody ListPaginationRequest listPaginationRequest, BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            List<AllRestaurantResponseDto> allRestaurantResponseDto = restaurantHandler.getAllRestaurants(listPaginationRequest);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(allRestaurantResponseDto);

        } catch (NoDataFoundException ex) {
            responseDto.setError(true);
            responseDto.setMessage("No se encontraron datos de restaurantes");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            responseDto.setError(true);
            responseDto.setMessage("Error interno del servidor");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private ResponseEntity<ResponseDto> ValidationErrors(BindingResult bindingResult, ResponseDto responseDto) {
        List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

        responseDto.setError(true);
        responseDto.setMessage("Error en las validaciones");
        responseDto.setData(errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }


}