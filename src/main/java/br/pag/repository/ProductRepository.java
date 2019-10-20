package br.pag.repository;

import br.pag.model.Product;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author brunner.klueger
 */
public interface ProductRepository extends CrudRepository<Product, Object>{
    List<Product> findByName(String name);
}
