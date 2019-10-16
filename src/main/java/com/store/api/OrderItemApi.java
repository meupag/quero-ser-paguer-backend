package com.store.api;

import com.store.domain.OrderItem;
import com.store.message.ExceptionMessage;
import com.store.resource.OrderItemResource;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Api(value = "orderItem")
@RequestMapping(value = "/customers/{customerId}/orders/{orderId}/order-items")
public interface OrderItemApi {

    @ApiOperation(value = "Add a new order item", nickname = "addOrderItem", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"orderItem",})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 405, message = "Invalid input")
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<OrderItemResource> addOrderItem(@ApiParam(value = "Customer id of the order item", required = true) @Valid @PathVariable Integer customerId,
                                                   @ApiParam(value = "Order id of the order item", required = true) @Valid @PathVariable Integer orderId,
                                                   @ApiParam(value = "Order item that will be added", required = true) @Valid @RequestBody OrderItem orderItem);

    @ApiOperation(value = "Get order item by id", nickname = "getOrderItemById", response = OrderItem.class,
            notes = "Retrieves a order item by id",
            authorizations = {@Authorization(value = "bearerAuth")}, tags = {"orderItem",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = OrderItem.class),
            @ApiResponse(code = 400, message = "Invalid status value")
    })
    @GetMapping(
            value = "/{orderItemId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<OrderItemResource> getOrderItemById(@ApiParam(value = "Customer id") @Valid @PathVariable Integer customerId,
                                                       @ApiParam(value = "Order id") @Valid @PathVariable Integer orderId,
                                                       @ApiParam(value = "Order item id") @Valid @PathVariable Integer orderItemId);

    @ApiOperation(value = "Get order items", nickname = "getOrderItem", response = OrderItem.class, responseContainer = "List",
            notes = "Retrieves a collection of users by taking into account order item features. Can be done pagination (quantity of items returned) and ordering",
            authorizations = {@Authorization(value = "bearerAuth")}, tags = {"orderItem",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = OrderItem.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")
    })
    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<PagedResources<OrderItem>> getOrderItem(@ApiParam(value = "Customer id") @Valid @PathVariable Integer customerId,
                                                           @ApiParam(value = "Order id") @Valid @PathVariable Integer orderId,
                                                           @ApiParam(value = "Order item id") @Valid @RequestParam(value = "id", required = false) Integer orderItemId,
                                                           @ApiParam(value = "Order item amount") @Valid @RequestParam(value = "amount", required = false) BigDecimal amount,
                                                           @ApiParam(value = "Order item price") @Valid @RequestParam(value = "price", required = false) BigDecimal price,
                                                           @PageableDefault Pageable pageable,
                                                           PagedResourcesAssembler assembler);


    @ApiOperation(value = "Update an existing order item", nickname = "updateOrderItemById", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"orderItem",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid order item id supplied"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 404, message = ExceptionMessage.OrderItemNotFound),
            @ApiResponse(code = 405, message = "Validation exception")
    })
    @PutMapping(
            value = "/{orderItemId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<OrderItemResource> updateOrderItemById(@ApiParam(value = "Customer id of the order item", required = true) @Valid @PathVariable Integer customerId,
                                                          @ApiParam(value = "Order id of the order item", required = true) @Valid @PathVariable Integer orderId,
                                                          @ApiParam(value = "Order item id that will be updated", required = true) @Valid @PathVariable Integer orderItemId,
                                                          @ApiParam(value = "Data that will be updated in order item", required = true) @Valid @RequestBody OrderItem orderItem);


    @ApiOperation(value = "Delete order item", nickname = "deleteOrderItemById", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"orderItem",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid order item id supplied"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 404, message = ExceptionMessage.OrderItemNotFound)
    })
    @DeleteMapping(
            value = "/{orderItemId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<Void> deleteOrderItemById(@ApiParam(value = "Customer id of the order item", required = true) @Valid @PathVariable Integer customerId,
                                             @ApiParam(value = "Order id of the order item", required = true) @Valid @PathVariable Integer orderId,
                                             @ApiParam(value = "Order item id that will be deleted", required = true) @Valid @PathVariable Integer orderItemId);

}
