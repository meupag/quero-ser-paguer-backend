package com.store.service;

import com.store.domain.Product;
import com.store.domain.ProductStatus;
import com.store.exception.ResourceNotFoundException;
import com.store.message.ExceptionMessage;
import com.store.repository.ProductRepository;
import com.store.util.PropertiesCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final PropertiesCopier copier;

    @Autowired
    public ProductService(ProductRepository productRepository, PropertiesCopier copier) {
        this.productRepository = productRepository;
        this.copier = copier;
    }


    public Product save(Product product) {
        product.setProductStatus(ProductStatus.ACTIVE);
        return productRepository.save(product);
    }


    public Product findProduct(Integer productId) {
        return findProductById(productId);
    }

    public Page<Product> findProductByExample(Product product, Pageable pageable) {
        return productRepository.findAll(Example.of(product), pageable);
    }

    public Product updateById(Integer productId, Product product) {
        product.setId(productId);
        Product persisted = findProductById(productId);
        copier.copyProperties(product, persisted);
        return persisted;
    }

    public void deleteById(Integer productId) {
        Product product = findProductById(productId);
        product.setProductStatus(ProductStatus.INACTIVE);
    }

    private Product findProductById(Integer productId) throws ResourceNotFoundException {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.ProductNotFound));
    }

}
