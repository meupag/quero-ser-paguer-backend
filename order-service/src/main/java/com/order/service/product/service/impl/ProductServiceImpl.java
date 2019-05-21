package com.order.service.product.service.impl;

import com.order.service.product.document.Product;
import com.order.service.product.exception.ProductNotFoundException;
import com.order.service.product.exception.ProductPreConditionException;
import com.order.service.product.repository.ProductRepository;
import com.order.service.product.service.ProductService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @CacheEvict(value = "list-product", allEntries = true)
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    @Cacheable(value = "list-product")
    public List<Product> findAll() {
        List<Product> products = this.productRepository.findAll();
        if(products.isEmpty()){
            throw new ProductNotFoundException();
        }
        return products;
    }

    @Override
    @Cacheable(value = "list-product")
    public Product findById(String id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        return productOptional.orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    @CacheEvict(value = "list-product", allEntries = true)
    public Product updateById(String id, Product product) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(!productOptional.isPresent()){
            throw new ProductPreConditionException(id);
        }
        product.setId(id);
        return this.productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "list-product", allEntries = true)
    public void deleteById(String id) {
        Optional<Product> orderOptional = this.productRepository.findById(id);
        if(!orderOptional.isPresent()){
            throw new ProductPreConditionException(id);
        }
        this.productRepository.delete(orderOptional.get());
    }

    @Override
    public void valid(String id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(!productOptional.isPresent()){
            throw new ProductPreConditionException(id);
        }
    }
}
