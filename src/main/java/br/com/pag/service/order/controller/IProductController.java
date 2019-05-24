package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.ProductCreateRequest;
import br.com.pag.service.order.controller.model.ProductResponse;
import br.com.pag.service.order.controller.model.ProductUpdateRequest;
import br.com.pag.service.order.exception.handler.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(value="pagorders", description="Operations for Pag! Orders Products")
public interface IProductController {

    @ApiOperation(
        value = "Creates a new Product for Pag!",
        nickname = "create",
        consumes = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<ProductResponse> create(final ProductCreateRequest request);

    @ApiOperation(
        value = "Updates a Pag! Product",
        nickname = "update",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<ProductResponse> update(ProductUpdateRequest request);

    @ApiOperation(
        value = "Finds all Pag! Products",
        nickname = "findAll",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<List<ProductResponse>> findAll();

    @ApiOperation(
        value = "Finds a Pag! Product by id",
        nickname = "findById",
        consumes = "application/json",
        produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<ProductResponse> findById(final Long productId);
}
