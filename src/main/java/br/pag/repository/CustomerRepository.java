package br.pag.repository;

import br.pag.model.Customer;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author brunner.klueger
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    List<Customer> findByName(String name);
}
