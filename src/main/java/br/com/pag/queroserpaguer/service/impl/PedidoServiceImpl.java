package br.com.pag.queroserpaguer.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.pag.queroserpaguer.domain.ItemPedido;
import br.com.pag.queroserpaguer.domain.Pedido;
import br.com.pag.queroserpaguer.repository.PedidoRepository;
import br.com.pag.queroserpaguer.service.ItemPedidoService;
import br.com.pag.queroserpaguer.service.PedidoService;

/**
 * Service Implementation for managing {@link Pedido}.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

	private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	private final PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoService itemPedidoService;

	public PedidoServiceImpl(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	/**
	 * Save a pedido.
	 *
	 * @param pedido the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Pedido save(Pedido pedido) {
		pedido.setValor(getValor(pedido.getItensPedido()));
		pedido =  pedidoRepository.save(pedido);
		updateItensPedido(pedido);
		
		return pedido; 
	}

	private void updateItensPedido(Pedido pedido) {
		List<ItemPedido> pedidosAntesSalvar = itemPedidoService.getItensByIdPedido(pedido.getId());
		pedido.getItensPedido().stream().forEach(p->{p.setPedido(pedido);itemPedidoService.save(p);});
		pedidosAntesSalvar.removeAll(pedido.getItensPedido());
		pedidosAntesSalvar.stream().forEach(p->{itemPedidoService.delete(p.getId());});
				
	}

	private BigDecimal getValor(Set<ItemPedido> itensPedido) {
		double valor = itensPedido.stream().map(item -> item.getPreco().multiply(item.getQuantidade())).mapToDouble(BigDecimal::doubleValue).sum();
		return new BigDecimal(valor);
	}

	/**
	 * Get all the pedidos.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findAll() {
		log.info("Obtendo lista de  Pedidos");
		return pedidoRepository.findAll();
	}


	/**
	 * Get one pedido by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Pedido> findById(Long id) {
		log.info("get Pedido : {}", id);
		return pedidoRepository.findById(id);
	}

	/**
	 * Delete the pedido by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.info("deletando o Pedido : {}", id);
		pedidoRepository.deleteById(id);
	}
}
