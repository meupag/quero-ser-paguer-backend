package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.ProductCreateRequest;
import br.com.pag.service.order.controller.model.ProductResponse;
import br.com.pag.service.order.controller.model.ProductUpdateRequest;
import br.com.pag.service.order.exception.ProductNotFoundByIdException;
import br.com.pag.service.order.exception.ProductsNotFoundException;
import br.com.pag.service.order.model.Produto;
import br.com.pag.service.order.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@Log4j2
@RestController
@RequestMapping("/products")
public class ProductController implements IProductController {

    @Autowired
    private ProductService productService;

    @Override
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid final ProductCreateRequest request) {
        Produto product = request.toModel();
        final Produto productCreated = productService.create(product);
        return ResponseEntity.ok(ProductResponse.fromModel(productCreated));
    }

    @Override
    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductUpdateRequest request) {
        Produto product = request.toModel();
        final Produto productUpdated = productService.update(product);
        return ResponseEntity.ok(ProductResponse.fromModel(productUpdated));
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductResponse>> findAll() {
        final List<Produto> products = productService.findAll();

        if (products.isEmpty()) {
            throw new ProductsNotFoundException();
        }

        final List<ProductResponse> productResponse = products.stream()
            .map(ProductResponse::fromModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(productResponse);
    }

    @Override
    @GetMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<ProductResponse> findById(@PathVariable final Long productId) {
        return productService.findById(productId)
            .map(c -> {
                return ResponseEntity.ok(ProductResponse.fromModel(c));
            })
            .orElseThrow(() -> new ProductNotFoundByIdException(productId));
    }
}
