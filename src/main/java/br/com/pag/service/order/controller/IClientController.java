package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.ClientCreateRequest;
import br.com.pag.service.order.controller.model.ClientResponse;
import br.com.pag.service.order.controller.model.ClientUpdateRequest;
import br.com.pag.service.order.exception.handler.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(value="pagorders", description="Operations for Pag! Orders Clients")
public interface IClientController {

    @ApiOperation(
        value = "Creates a new Client for Pag!",
        nickname = "create",
        consumes = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<ClientResponse> create(ClientCreateRequest request);

    @ApiOperation(
        value = "Updates a Pag! Client",
        nickname = "update",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<ClientResponse> update(ClientUpdateRequest request);

    @ApiOperation(
        value = "Finds all Pag! Clients",
        nickname = "findAll",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<List<ClientResponse>> findAll();

    @ApiOperation(
        value = "Finds a Pag! Client by id",
        nickname = "findById",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<ClientResponse> findById(Long clientId);

}
