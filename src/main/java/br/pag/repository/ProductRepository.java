package br.pag.repository;

import br.pag.model.Product;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author brunner.klueger
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Object> {

    List<Product> findByName(String name);
}
