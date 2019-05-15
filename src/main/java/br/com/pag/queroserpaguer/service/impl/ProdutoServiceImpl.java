package br.com.pag.queroserpaguer.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pag.queroserpaguer.domain.Produto;
import br.com.pag.queroserpaguer.domain.Produto;
import br.com.pag.queroserpaguer.repository.ProdutoRepository;
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

    
    @Override
    public Produto save(Produto produto) {
        log.info("Salvando  Produto : {}", produto);
        return produtoRepository.save(produto);
    }
    
    @Override
    public Optional<Produto> update(Long id, Produto produto) {
        log.info("atualizando Produto  : {}", produto);
        return produtoRepository.findById(id)
        	.map(updateProduto -> {
        		updateProduto.setNome(produto.getNome());
				updateProduto.setPrecoSugerido(produto.getPrecoSugerido()	);
				updateProduto.setNome(produto.getNome());
				return  produtoRepository.save(updateProduto);
			}
		);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Produto> findAll() {
        log.info("Obtendo lista de  Produtos");
        return produtoRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Produto> findById(Long id) {
        log.info("get Produto : {}", id);
        return produtoRepository.findById(id);
    }

   
    @Override
    public void delete(Long id) {
        log.info("deletando o Produto : {}", id);
        produtoRepository.deleteById(id);
    }
}
