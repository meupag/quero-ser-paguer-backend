package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.OrderCreateRequest;
import br.com.pag.service.order.controller.model.OrderItemResponse;
import br.com.pag.service.order.controller.model.OrderResponse;
import br.com.pag.service.order.controller.model.OrderUpdateRequest;
import br.com.pag.service.order.exception.handler.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(value="pagorders", description="Operations for Pag! Orders")
public interface IOrderController {

    @ApiOperation(
        value = "Creates a new Order for Pag!",
        nickname = "create",
        consumes = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<OrderResponse> create(final OrderCreateRequest request);

    @ApiOperation(
        value = "Finds all Pag! Orders",
        nickname = "findAll",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<List<OrderResponse>> findAll();

    @ApiOperation(
        value = "Finds a Pag! Order by id",
        nickname = "findById",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<OrderResponse> findById(final Long orderId);

    @ApiOperation(
        value = "Updates a Pag! Order",
        nickname = "update",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<OrderResponse> update(final OrderUpdateRequest request);

    @ApiOperation(
        value = "Updates a Pag! Order",
        nickname = "update",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<OrderResponse> delete(@PathVariable final Long orderId);

    @ApiOperation(
        value = "Updates a Pag! Order Item",
        nickname = "update",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<OrderItemResponse> deleteItem(@PathVariable final Long orderId, @PathVariable final Long orderItemId);
}
