package br.com.pag.produto;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;



@org.springframework.stereotype.Repository
public interface ProdutoRepository extends Repository<Produto, Long> {

	static final String CACHE_NAME = "produtos";
	
	
	@Cacheable(CACHE_NAME)
	public Optional<Produto> findById(Long id);
	
	@Cacheable(CACHE_NAME)	
	public boolean existsById(Long id);
	
	@Cacheable(CACHE_NAME)
	public Iterable<Produto> findAll();
	
	@Cacheable(CACHE_NAME)
	public Iterable<Produto> findAllById(Iterable<Long> ids);
	
	
	@CachePut(CACHE_NAME)
	public Produto save(Produto entity);
	
	@CachePut(CACHE_NAME)
	public Iterable<Produto> saveAll(Iterable<Produto> entities);
	
	
	@CacheEvict(CACHE_NAME)
	public void deleteById(Long id);
	
	@CacheEvict(CACHE_NAME)
	public void delete(Produto entity);
	
	@CacheEvict(CACHE_NAME)
	public void deleteAll(Iterable<Produto> entities);
	
	@CacheEvict(CACHE_NAME)
	public void deleteAll();
    
}
