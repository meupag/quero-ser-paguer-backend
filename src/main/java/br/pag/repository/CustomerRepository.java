package br.pag.repository;

import br.pag.model.Customer;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author brunner.klueger
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findBySocialSecurityNumber(String socialSecurityNumber);
}
