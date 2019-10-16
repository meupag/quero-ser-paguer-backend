package com.store.api;

import com.store.domain.Product;
import com.store.message.ExceptionMessage;
import com.store.resource.ProductResource;
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
import java.math.BigDecimal;

@Api(value = "product")
@RequestMapping(value = "/products")
@ExposesResourceFor(Product.class)
public interface ProductApi {

    @ApiOperation(value = "Add a new customer", nickname = "addProduct", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 405, message = "Invalid input")
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<ProductResource> addProduct(@ApiParam(value = "Product that will be added", required = true) @Valid @RequestBody Product product);

    @ApiOperation(value = "Get product by id", nickname = "getProductById", response = Product.class,
            notes = "Retrieves a product by id",
            authorizations = {@Authorization(value = "bearerAuth")}, tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Product.class),
            @ApiResponse(code = 400, message = "Invalid status value")
    })
    @GetMapping(
            value = "/{productId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<ProductResource> getProductById(@PathVariable Integer productId);

    @ApiOperation(value = "Get products", nickname = "getProduct", response = Product.class, responseContainer = "List",
            notes = "Retrieves a collection of users by taking into account product features. Can be done pagination (quantity of items returned) and ordering",
            authorizations = {@Authorization(value = "bearerAuth")}, tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")
    })
    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<PagedResources<Product>> getProduct(@ApiParam(value = "Product id") @Valid @RequestParam(value = "id", required = false) Integer productId,
                                                       @ApiParam(value = "Product name") @Valid @RequestParam(value = "name", required = false) String nome,
                                                       @ApiParam(value = "Product suggested price") @Valid @RequestParam(value = "suggested-price", required = false) BigDecimal suggestedPrice,
                                                       @PageableDefault Pageable pageable,
                                                       PagedResourcesAssembler assembler);


    @ApiOperation(value = "Update an existing product", nickname = "updateProductById", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid customer id supplied"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 404, message = ExceptionMessage.ProductNotFound),
            @ApiResponse(code = 405, message = "Validation exception")
    })
    @PutMapping(
            value = "/{productId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<ProductResource> updateProductById(@ApiParam(value = "Product id that will be updated", required = true) @Valid @PathVariable Integer productId,
                                                      @ApiParam(value = "Data that will be updated in product", required = true) @Valid @RequestBody Product product);


    @ApiOperation(value = "Delete product", nickname = "deleteProductById", authorizations = {@Authorization(value = "bearerAuth")}, tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid customer id supplied"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 404, message = ExceptionMessage.ProductNotFound)
    })
    @DeleteMapping(
            value = "/{productId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<Void> deleteProductById(@ApiParam(value = "Product id that will be deleted", required = true) @Valid @PathVariable Integer productId);

}
