package br.com.pag.queroserpaguer.repository;


import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pag.queroserpaguer.domain.ItemPedido;


/**
 * Spring Data  repository for the ItemPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

	@Query("From ItemPedido item where item.pedido.id = :idPedido  ")
	List<ItemPedido> getItensByIdPedido(@Param("idPedido") Long idPedido);
}
