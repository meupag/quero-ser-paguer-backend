package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.ProductCreateRequest;
import br.com.pag.service.order.controller.model.ProductListResponse;
import br.com.pag.service.order.controller.model.ProductResponse;
import br.com.pag.service.order.controller.model.ProductUpdateRequest;
import br.com.pag.service.order.exception.ProductNotFoundByIdException;
import br.com.pag.service.order.exception.ProductsNotFoundException;
import br.com.pag.service.order.model.Produto;
import br.com.pag.service.order.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/products")
public class ProductController implements IProductController {

    @Autowired
    private ProductService productService;

    @Override
    @PreAuthorize("hasRole('product_write')")
    @PostMapping(produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid final ProductCreateRequest request) {
        Produto product = request.toModel();
        final Produto productCreated = productService.create(product);
        final ProductResponse productResponse = ProductResponse.fromModel(productCreated);

        addLinks(productCreated, productResponse);

        return ResponseEntity.ok(productResponse);
    }

    @Override
    @PreAuthorize("hasRole('product_write')")
    @PutMapping(produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductUpdateRequest request) {
        Produto product = request.toModel();
        final Produto productUpdated = productService.update(product);
        final ProductResponse productResponse = ProductResponse.fromModel(productUpdated);

        addLinks(productUpdated, productResponse);

        return ResponseEntity.ok(productResponse);
    }

    @Override
    @PreAuthorize("hasRole('product_read') or hasRole('product_write')")
    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<ProductListResponse> findAll() {
        final List<Produto> products = productService.findAll();

        if (products.isEmpty()) {
            throw new ProductsNotFoundException();
        }

        final List<ProductResponse> productsResponse = products.stream()
            .map(ProductResponse::fromModel)
            .map(this::addSelfLink)
            .collect(Collectors.toList());

        final ProductListResponse listResponse = new ProductListResponse(productsResponse);
        addAllLink(listResponse);

        return ResponseEntity.ok(listResponse);
    }

    @Override
    @PreAuthorize("hasRole('product_read') or hasRole('product_write')")
    @GetMapping(value = "/{productId}", produces = "application/hal+json")
    public ResponseEntity<ProductResponse> findById(@PathVariable final Long productId) {
        return productService.findById(productId)
            .map(c -> {
                final ProductResponse productResponse = ProductResponse.fromModel(c);
                addAllLink(productResponse);
                return ResponseEntity.ok(productResponse);
            })
            .orElseThrow(() -> new ProductNotFoundByIdException(productId));
    }

    private void addLinks(Produto product, ProductResponse productResponse) {
        Link allLink = linkTo(methodOn(ProductController.class).findAll()).withRel("allProducts");
        Link selfLink = linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel();

        productResponse.add(allLink);
        productResponse.add(selfLink);
    }

    private ProductResponse addSelfLink(ProductResponse productResponse) {
        Link selfLink = linkTo(methodOn(ProductController.class).findById(productResponse.getProductId())).withSelfRel();
        productResponse.add(selfLink);
        return productResponse;
    }

    private void addAllLink(ProductResponse productResponse) {
        Link allLink = linkTo(methodOn(ProductController.class).findAll()).withRel("allProducts");
        productResponse.add(allLink);
    }

    private void addAllLink(ProductListResponse listResponse) {
        Link allLink = linkTo(methodOn(ProductController.class).findAll()).withRel("allProducts");
        listResponse.add(allLink);
    }
}
