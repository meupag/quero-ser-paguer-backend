package br.pag.repository;

import br.pag.model.Order;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author brunner.klueger
 */
public interface OrderRepository extends CrudRepository<Order, Object> {

}
