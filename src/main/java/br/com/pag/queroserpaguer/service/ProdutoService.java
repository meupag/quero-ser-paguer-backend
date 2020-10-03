package br.com.pag.queroserpaguer.service;


import java.util.List;
import java.util.Optional;

import br.com.pag.queroserpaguer.domain.Produto;
import br.com.pag.queroserpaguer.domain.Produto;

/**
 * Service Interface for managing {@link Produto}.
 * @author igor.nunes
 */
public interface ProdutoService {

	 /**
     * Save a produto.
     *
     * @param produto the entity to save.
     * @return the persisted entity.
     */
    Produto save(Produto produto);

    /**
     * Get all the produtos.
     *
     * @return the list of entities.
     */
    List<Produto> findAll();


    /**
     * Get the "id" produto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Produto> findById(Long id);

    /**
     * Delete the "id" produto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Update a produto 
     * 
     * @param id
     * @param produto
     * @return
     */
	Optional<Produto> update(Long id, Produto produto);
}
