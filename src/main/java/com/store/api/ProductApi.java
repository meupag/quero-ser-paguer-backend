package com.store.api;

import com.store.domain.Product;
import com.store.message.ExceptionMessage;
import com.store.message.ValidatorFieldMessage;
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

    @ApiOperation(value = "Add a new customer", nickname = "addProduct", tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = ValidatorFieldMessage.InvalidInput)
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<ProductResource> addProduct(@ApiParam(value = "Product that will be added", required = true) @Valid @RequestBody Product product);

    @ApiOperation(value = "Get product by id", nickname = "getProductById", response = Product.class,
            notes = "Retrieves a product by id",
            tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Product.class),
            @ApiResponse(code = 404, message = ExceptionMessage.ProductNotFound),
    })
    @GetMapping(
            value = "/{productId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<ProductResource> getProductById(@ApiParam(value = "Product id", example = "1") Integer productId);

    @ApiOperation(value = "Get products", nickname = "getProduct", response = Product.class, responseContainer = "List",
            notes = "Retrieves a collection of users by taking into account product features. Can be done pagination (quantity of items returned) and ordering",
            tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = ExceptionMessage.ProductNotFound)
    })
    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<PagedResources<Product>> getProduct(@ApiParam(value = "Product id", example = "1") @Valid @RequestParam(value = "id", required = false) Integer productId,
                                                       @ApiParam(value = "Product name") @Valid @RequestParam(value = "name", required = false) String nome,
                                                       @ApiParam(value = "Product suggested price", example = "1.1") @Valid @RequestParam(value = "suggested-price", required = false) BigDecimal suggestedPrice,
                                                       @PageableDefault Pageable pageable,
                                                       PagedResourcesAssembler assembler);


    @ApiOperation(value = "Update an existing product", nickname = "updateProductById", tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = ExceptionMessage.ProductNotFound),
            @ApiResponse(code = 405, message = ValidatorFieldMessage.InvalidInput)
    })
    @PutMapping(
            value = "/{productId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<ProductResource> updateProductById(@ApiParam(value = "Product id that will be updated", example = "1", required = true) @Valid @PathVariable Integer productId,
                                                      @ApiParam(value = "Data that will be updated in product", required = true) @Valid @RequestBody Product product);


    @ApiOperation(value = "Delete product", nickname = "deleteProductById", tags = {"product",})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = ExceptionMessage.ProductNotFound)
    })
    @DeleteMapping(
            value = "/{productId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<Void> deleteProductById(@ApiParam(value = "Product id that will be deleted", example = "1", required = true) @Valid @PathVariable Integer productId);

}
