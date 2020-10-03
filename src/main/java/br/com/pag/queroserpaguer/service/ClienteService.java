package br.com.pag.queroserpaguer.service;


import java.util.List;
import java.util.Optional;

import br.com.pag.queroserpaguer.domain.Cliente;

/**
 * Service Interface for managing {@link Cliente}.
 * 
 * @author igor.nunes
 */
public interface ClienteService {

    /**
     * Save a cliente.
     *
     * @param cliente the entity to save.
     * @return the persisted entity.
     */
    Cliente save(Cliente cliente);

    /**
     * Get all the clientes.
     *
     * @return the list of entities.
     */
    List<Cliente> findAll();


    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cliente> findById(Long id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Update a cliente 
     * 
     * @param id
     * @param cliente
     * @return
     */
	Optional<Cliente> update(Long id, Cliente cliente);
}
