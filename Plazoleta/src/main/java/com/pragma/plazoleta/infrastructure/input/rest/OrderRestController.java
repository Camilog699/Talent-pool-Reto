package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderStateResponseDto;
import com.pragma.plazoleta.application.dto.response.ResponseDto;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import com.pragma.plazoleta.common.OrderState;
import com.pragma.plazoleta.common.exception.CategoryNotFoundException;
import com.pragma.plazoleta.common.exception.NotEnoughPrivilegesException;
import com.pragma.plazoleta.common.exception.RestaurantNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor

public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "You don't have enough privileges to perform this action",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "User or restaurant not found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("/")
    public ResponseEntity<ResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto, BindingResult bindingResult) {

        ResponseDto responseDto = new ResponseDto();

        if (bindingResult.hasErrors()) {
            return validationErrors(bindingResult, responseDto);
        }

        try {
            OrderResponseDto orderResponseDto = orderHandler.createOrder(orderRequestDto);

            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(orderResponseDto);

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
            responseDto.setMessage("You don't have enough privileges to perform this action");
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

    @Operation(summary = "List all orders by order state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orders listed",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class)))),
    })
    @RolesAllowed({"ROLE_EMPLEADO"})
    @GetMapping("/get/{orderState}")
    public ResponseEntity<ResponseDto> getOrderByOrderState(@PathVariable OrderState orderState) {
        ResponseDto responseDto = new ResponseDto();

        try {
            List<OrderStateResponseDto> orderStateResponseDtoList = orderHandler.getAllOrdersByOrderState(orderState);

            responseDto.setError(false);
            responseDto.setMessage(null);
            responseDto.setData(orderStateResponseDtoList);
        }  catch (NotEnoughPrivilegesException ex) {
            responseDto.setError(true);
            responseDto.setMessage("You don't have enough privileges to perform this action");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
        }    catch (Exception ex) {
            responseDto.setError(true);
            responseDto.setMessage("Internal server error");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private ResponseEntity<ResponseDto> validationErrors(BindingResult bindingResult, ResponseDto responseDto) {
        List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        responseDto.setError(true);
        responseDto.setMessage("Validation errors");
        responseDto.setData(errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }


}