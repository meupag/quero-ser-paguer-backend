package br.pag.repository;

import br.pag.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author brunner.klueger
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Object> {

}
