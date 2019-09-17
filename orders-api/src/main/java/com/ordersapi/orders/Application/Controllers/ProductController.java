package com.ordersapi.orders.Application.Controllers;

import com.ordersapi.orders.Application.ViewModel.ProductViewModel;
import com.ordersapi.orders.Domain.Entities.Produto;
import com.ordersapi.orders.Domain.Services.ProductDomainService;
import com.ordersapi.orders.Infrasctructure.Models.ProductModel;
import com.ordersapi.orders.Infrasctructure.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    private ProductDomainService productDomainService;


    @PostConstruct
    public void init() {
        productDomainService = new ProductDomainService(productRepository);
    }

    @GetMapping("/product")
    public List<Produto> getAllOrders() {
        return productDomainService.listProduct();
    }

    @GetMapping("/product/{id}")
    public Produto getORdersById(@PathVariable(value = "id") UUID id) {
        return productDomainService.getById(id);
    }

    @PostMapping("/product")
    public Produto createOrders(@Valid @RequestBody ProductViewModel order) throws Exception {
        ProductModel product = new ProductModel(order.nome, order.precoSugerido);
        try{
            return productDomainService.createProduct(product);
        }catch (Exception err){
            throw err;
        }
    };
}
