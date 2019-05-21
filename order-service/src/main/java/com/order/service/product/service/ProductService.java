package com.order.service.product.service;

import com.order.service.product.document.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    Product findById(String id);

    Product updateById(String id, Product product);

    void deleteById(String id);

    void valid(String id);
}
