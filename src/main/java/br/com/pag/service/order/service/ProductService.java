package br.com.pag.service.order.service;

import br.com.pag.service.order.exception.ProductNotFoundByIdException;
import br.com.pag.service.order.model.Produto;
import br.com.pag.service.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Produto create(Produto product) {
        return repository.save(product);
    }

    public Produto update(Produto product) {
        return repository.findById(product.getId())
            .map(c -> {
                c.setNome(product.getNome())
                    .setPrecoSugerido(product.getPrecoSugerido());
                return repository.save(c);
            })
            .orElseThrow(() -> new ProductNotFoundByIdException(product.getId()));
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Optional<Produto> findById(Long productId) {
        return repository.findById(productId);
    }
}
