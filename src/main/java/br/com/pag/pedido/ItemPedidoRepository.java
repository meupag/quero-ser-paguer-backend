package br.com.pag.pedido;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;


@org.springframework.stereotype.Repository
public interface ItemPedidoRepository extends Repository<ItemPedido, Long> {

	static final String CACHE_NAME = "pedidos";
	

	@Cacheable(CACHE_NAME)
	public Optional<ItemPedido> findById(Long id);

	@Cacheable(CACHE_NAME)	
	public boolean existsById(Long id);

	@Cacheable(CACHE_NAME)
	public Iterable<ItemPedido> findAll();

	@Cacheable(CACHE_NAME)
	public Iterable<ItemPedido> findAllById(Iterable<Long> ids);
	
    
	@CachePut(CACHE_NAME)
	public ItemPedido save(ItemPedido entity);

    @CachePut(CACHE_NAME)
	public Iterable<ItemPedido> saveAll(Iterable<ItemPedido> entities);


    @CacheEvict(CACHE_NAME)
	public void deleteById(Long id);

	@CacheEvict(CACHE_NAME)
	public void delete(ItemPedido entity);

	@CacheEvict(CACHE_NAME)
	public void deleteAll(Iterable<ItemPedido> entities);

	@CacheEvict(CACHE_NAME)
	public void deleteAll();
    
}
