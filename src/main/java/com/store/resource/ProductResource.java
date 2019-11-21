package com.store.resource;

import com.store.controller.ProductController;
import com.store.domain.Product;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class ProductResource extends ResourceSupport {

    private final Product product;

    public ProductResource(Product product) {
        this.product = product;
        final Integer productId = product.getId();
        add(linkTo(ProductController.class).withRel("products"));
        add(linkTo(methodOn(ProductController.class).getProductById(productId)).withSelfRel());
    }

}