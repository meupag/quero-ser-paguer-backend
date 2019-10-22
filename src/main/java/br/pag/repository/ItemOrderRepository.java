package br.pag.repository;

import br.pag.model.ItemOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author brunner.klueger
 */
public interface ItemOrderRepository extends PagingAndSortingRepository<ItemOrder, Long> {

}
