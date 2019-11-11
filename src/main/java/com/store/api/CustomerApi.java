package com.store.api;

import com.store.domain.Customer;
import com.store.message.ExceptionMessage;
import com.store.message.ValidatorFieldMessage;
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

    @ApiOperation(value = "Add a new customer", nickname = "addCustomer", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = ValidatorFieldMessage.InvalidInput)
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<CustomerResource> addCustomer(@ApiParam(value = "Customer that will be added", required = true) @Valid @RequestBody Customer customer);

    @ApiOperation(value = "Get customer by id", nickname = "getCustomerById", response = Customer.class,
            notes = "Retrieves a customer by id", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Customer.class),
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound)
    })
    @GetMapping(
            value = "/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<CustomerResource> getCustomerById(@ApiParam(value = "Customer id", example = "1") @Valid @PathVariable Integer customerId);

    @ApiOperation(value = "Get customers", nickname = "getCustomer", response = Customer.class, responseContainer = "List",
            notes = "Retrieves a collection of users by taking into account customer features. Can be done pagination (quantity of items returned) and ordering",
            tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Customer.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound)
    })
    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<PagedResources<CustomerResource>> getCustomer(@ApiParam(value = "Customer id", example = "1") @Valid @RequestParam(value = "id", required = false) Integer customerId,
                                                                 @ApiParam(value = "Customer name") @Valid @RequestParam(value = "name", required = false) String name,
                                                                 @ApiParam(value = "Customer cpf") @Valid @RequestParam(value = "cpf", required = false) String cpf,
                                                                 @ApiParam(value = "Customer birth date") @Valid @RequestParam(value = "birth-date", required = false) LocalDate birthDate,
                                                                 @PageableDefault Pageable pageable,
                                                                 PagedResourcesAssembler assembler);

    @ApiOperation(value = "Update an existing customer", nickname = "updateCustomerById", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound),
            @ApiResponse(code = 405, message = ValidatorFieldMessage.InvalidInput)
    })
    @PutMapping(
            value = "/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<CustomerResource> updateCustomerById(@ApiParam(value = "Customer id that will be updated", example = "1", required = true) @Valid @PathVariable Integer customerId,
                                                        @ApiParam(value = "Data that will be updated in customer", required = true) @Valid @RequestBody Customer customer);


    @ApiOperation(value = "Delete customer", nickname = "deleteCustomerById", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound)
    })
    @DeleteMapping(
            value = "/{customerId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<Void> deleteCustomerById(@ApiParam(value = "Customer id that will be deleted", example = "1", required = true) @Valid @PathVariable Integer customerId);

}
