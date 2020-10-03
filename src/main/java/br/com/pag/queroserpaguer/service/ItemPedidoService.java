package br.com.pag.queroserpaguer.service;


import java.util.List;
import java.util.Optional;

import br.com.pag.queroserpaguer.domain.ItemPedido;
import br.com.pag.queroserpaguer.domain.ItemPedido;

/**
 * Service Interface for managing {@link ItemPedido}.
 * @author igor.nunes
 */
public interface ItemPedidoService {

	 /**
     * Save a itemPedido.
     *
     * @param itemPedido the entity to save.
     * @return the persisted entity.
     */
    ItemPedido save(ItemPedido itemPedido);

    /**
     * Get all the itemPedidos.
     *
     * @return the list of entities.
     */
    List<ItemPedido> findAll();


    /**
     * Get the "id" itemPedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemPedido> findById(Long id);

    /**
     * Delete the "id" itemPedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Update a itemPedido 
     * 
     * @param id
     * @param itemPedido
     * @return
     */
	Optional<ItemPedido> update(Long id, ItemPedido itemPedido);
    
    /**
     * retorna os itens de um Pedido.
     * 
     * @param idPedido
     * @return
     */
    List<ItemPedido> getItensByIdPedido(Long idPedido);
}
