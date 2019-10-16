package com.store.api;

import com.store.domain.Customer;
import com.store.message.ExceptionMessage;
import com.store.resource.CustomerResource;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(value = "customer")
@RequestMapping(value = "/customers")
@ExposesResourceFor(Customer.class)
public interface CustomerApi {

    @ApiOperation(value = "Add a new customer", nickname = "addCustomer", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"customer",})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 405, message = "Invalid input")
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<CustomerResource> addCustomer(@ApiParam(value = "Customer that will be added", required = true) @Valid @RequestBody Customer customer);

    @ApiOperation(value = "Get customer by id", nickname = "getCustomerById", response = Customer.class,
            notes = "Retrieves a customer by id",
            authorizations = {@Authorization(value = "bearerAuth")}, tags = {"customer",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Customer.class),
            @ApiResponse(code = 400, message = "Invalid status value")
    })
    @GetMapping(
            value = "/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<CustomerResource> getCustomerById(@ApiParam(value = "Customer id") @Valid @PathVariable Integer customerId);

    @ApiOperation(value = "Get customers", nickname = "getCustomer", response = Customer.class, responseContainer = "List",
            notes = "Retrieves a collection of users by taking into account customer features. Can be done pagination (quantity of items returned) and ordering",
            authorizations = {@Authorization(value = "bearerAuth")}, tags = {"customer",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Customer.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")
    })
    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<PagedResources<CustomerResource>> getCustomer(@ApiParam(value = "Customer id") @Valid @RequestParam(value = "id", required = false) Integer customerId,
                                                                 @ApiParam(value = "Customer name") @Valid @RequestParam(value = "name", required = false) String name,
                                                                 @ApiParam(value = "Customer cpf") @Valid @RequestParam(value = "cpf", required = false) String cpf,
                                                                 @ApiParam(value = "Customer birth date") @Valid @RequestParam(value = "birth-date", required = false) LocalDate birthDate,
                                                                 @PageableDefault Pageable pageable,
                                                                 PagedResourcesAssembler assembler);

    @ApiOperation(value = "Update an existing customer", nickname = "updateCustomerById", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"customer",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid customer id supplied"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound),
            @ApiResponse(code = 405, message = "Validation exception")
    })
    @PutMapping(
            value = "/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<CustomerResource> updateCustomerById(@ApiParam(value = "Customer id that will be updated", required = true) @Valid @PathVariable Integer customerId,
                                                        @ApiParam(value = "Data that will be updated in customer", required = true) @Valid @RequestBody Customer customer);


    @ApiOperation(value = "Delete customer", nickname = "deleteCustomerById", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"customer",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid customer id supplied"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound)
    })
    @DeleteMapping(
            value = "/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<Void> deleteCustomerById(@ApiParam(value = "Customer id that will be deleted", required = true) @Valid @PathVariable Integer customerId);

}
