package br.com.pag.pedido;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;


@org.springframework.stereotype.Repository
public interface PedidoRepository extends Repository<Pedido, Long> {

	static final String CACHE_NAME = "pedidos";
	

	@Cacheable(CACHE_NAME)
	public Optional<Pedido> findById(Long id);

	@Cacheable(CACHE_NAME)	
	public boolean existsById(Long id);

	@Cacheable(CACHE_NAME)
	public Iterable<Pedido> findAll();

	@Cacheable(CACHE_NAME)
	public Iterable<Pedido> findAllById(Iterable<Long> ids);
	
    
	@CachePut(CACHE_NAME)
	public Pedido save(Pedido entity);

    @CachePut(CACHE_NAME)
	public Iterable<Pedido> saveAll(Iterable<Pedido> entities);


    @CacheEvict(CACHE_NAME)
	public void deleteById(Long id);

	@CacheEvict(CACHE_NAME)
	public void delete(Pedido entity);

	@CacheEvict(CACHE_NAME)
	public void deleteAll(Iterable<Pedido> entities);

	@CacheEvict(CACHE_NAME)
	public void deleteAll();
    
}
