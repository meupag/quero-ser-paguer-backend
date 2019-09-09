package br.com.pag.cliente;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;


@org.springframework.stereotype.Repository
public interface ClienteRepository extends Repository<Cliente, Long> {

	static final String CACHE_NAME = "clientes";
	

	@Cacheable(CACHE_NAME)
	public Optional<Cliente> findById(Long id);

	@Cacheable(CACHE_NAME)	
	public boolean existsById(Long id);

	@Cacheable(CACHE_NAME)
	public Iterable<Cliente> findAll();

	@Cacheable(CACHE_NAME)
	public Iterable<Cliente> findAllById(Iterable<Long> ids);
	
    
	@CachePut(CACHE_NAME)
	public Cliente save(Cliente entity);

    @CachePut(CACHE_NAME)
	public Iterable<Cliente> saveAll(Iterable<Cliente> entities);


    @CacheEvict(CACHE_NAME)
	public void deleteById(Long id);

	@CacheEvict(CACHE_NAME)
	public void delete(Cliente entity);

	@CacheEvict(CACHE_NAME)
	public void deleteAll(Iterable<Cliente> entities);

	@CacheEvict(CACHE_NAME)
	public void deleteAll();
    
}
