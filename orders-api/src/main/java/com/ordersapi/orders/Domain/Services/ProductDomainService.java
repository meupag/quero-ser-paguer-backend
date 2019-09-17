package com.ordersapi.orders.Domain.Services;

import com.ordersapi.orders.Domain.Entities.Produto;
import com.ordersapi.orders.Domain.Repositories.IProductRepository;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

public class ProductDomainService {
    private IProductRepository _productRepository;
    public ProductDomainService(IProductRepository productRepository){
        _productRepository = productRepository;
    }


    public Produto createProduct(Produto produto){
        return (Produto) _productRepository.save(produto);
    };

    public List<Produto> listProduct(){
        return _productRepository.findAll();
    };

    public Produto getById(UUID Id) {
      return  (Produto) _productRepository.findAllById(Id);
    };

}
