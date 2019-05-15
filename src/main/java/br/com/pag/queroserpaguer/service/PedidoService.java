package br.com.pag.queroserpaguer.service;


import java.util.List;
import java.util.Optional;

import br.com.pag.queroserpaguer.domain.Pedido;

/**
 * Service Interface for managing {@link Pedido}.
 * @author igor.nunes
 */
public interface PedidoService {

    /**
     * Save a pedido.
     *
     * @param pedido the entity to save.
     * @return the persisted entity.
     */
    Pedido save(Pedido pedido);

    /**
     * Get all the pedidos.
     *
     * @return the list of entities.
     */
    List<Pedido> findAll();


    /**
     * Get the "id" pedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pedido> findById(Long id);

    /**
     * Delete the "id" pedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<Pedido> update(Long id, Pedido pedido);
}
