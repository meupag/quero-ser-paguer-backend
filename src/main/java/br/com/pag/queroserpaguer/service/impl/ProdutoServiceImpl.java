package br.com.pag.queroserpaguer.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pag.queroserpaguer.domain.Produto;
import br.com.pag.queroserpaguer.repository.ProdutoRepository;
import br.com.pag.queroserpaguer.service.ProdutoService;

/**
 * Service Implementation for managing {@link Produto}.
 */
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Save a produto.
     *
     * @param produto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Produto save(Produto produto) {
        log.info("Salvando  Produto : {}", produto);
        return produtoRepository.save(produto);
    }

    /**
     * Get all the produtos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Produto> findAll() {
        log.info("Obtendo lista de  Produtos");
        return produtoRepository.findAll();
    }


    /**
     * Get one produto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Produto> findById(Long id) {
        log.info("get Produto : {}", id);
        return produtoRepository.findById(id);
    }

    /**
     * Delete the produto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.info("deletando o Produto : {}", id);
        produtoRepository.deleteById(id);
    }
}
