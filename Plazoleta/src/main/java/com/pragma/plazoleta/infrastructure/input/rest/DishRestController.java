package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.ListPaginationRequest;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;
import com.pragma.plazoleta.application.handler.IDishHandler;
import com.pragma.plazoleta.common.exception.CategoryNotFoundException;
import com.pragma.plazoleta.common.exception.NoDataFoundException;
import com.pragma.plazoleta.common.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
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
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor

public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Save new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Dish already exists", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not enough privileges", content = @Content)
    })
    @RolesAllowed("ROLE_PROPIETARIO")
    @PostMapping("/")
    public ResponseEntity<ResponseDto> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto,
                                                BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            DishResponseDto dishResponseDto = dishHandler.saveDish(dishRequestDto);

            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(dishResponseDto);

        } catch (CategoryNotFoundException ex) {
            responseDto.setError(true);
            responseDto.setMessage("Category not found");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (RestaurantNotFoundException ex) {
            responseDto.setError(true);
            responseDto.setMessage("Restaurant not found");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (NotEnoughPrivilegesException ex) {
            responseDto.setError(true);
            responseDto.setMessage("You don't have enough privileges to perform this action, only the owner can do it");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All dishes returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<DishResponseDto>> getAllDishes() {
        return ResponseEntity.ok(dishHandler.getAllDishes());
    }

    @Operation(summary = "Update dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateDish(@PathVariable Long id, @RequestBody DishUpdateRequestDto dishUpdateRequestDto,
                                                  BindingResult bindingResult) {

        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            DishResponseDto dishResponseDto = dishHandler.updateDish(id, dishUpdateRequestDto);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(dishResponseDto);
        } catch (NotEnoughPrivilegesException ex) {
            responseDto.setError(true);
            responseDto.setMessage("You don't have enough privileges to perform this action");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Enable or disable an dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish enabled", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content)
    })
    @RolesAllowed({"ROLE_PROPIETARIO"})
    @PutMapping("/enable/{id}")
    public ResponseEntity<ResponseDto> enableDish(@PathVariable Long id) {

        ResponseDto responseDto = new ResponseDto();

        try {
            DishResponseDto dishResponseDto = dishHandler.enableDish(id);
            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(dishResponseDto);
        } catch (NotEnoughPrivilegesException ex) {
            responseDto.setError(true);
            responseDto.setMessage("You don't have enough privileges to perform this action");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "Get all restaurant dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All dishes listed",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))),
    })
    @GetMapping("/alldishes/{id}")
    public ResponseEntity<ResponseDto> getAllDishesByRestaurantId(@Valid @RequestBody ListPaginationRequest listPaginationRequest,
                                                                  @PathVariable Long id,
                                                                  BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return ValidationErrors(bindingResult, responseDto);
        }

        try {
            responseDto.setError(false);
            List<DishResponseDto> dishResponseDtoList = dishHandler.getDishByRestaurantId(listPaginationRequest, id);
            responseDto.setMessage(null);
            responseDto.setData(dishResponseDtoList);

        } catch (NoDataFoundException ex) {
            responseDto.setError(true);
            responseDto.setMessage("No data found");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
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