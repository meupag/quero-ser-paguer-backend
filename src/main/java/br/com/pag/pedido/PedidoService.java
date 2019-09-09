package br.com.pag.pedido;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public void save(Pedido cliente) {
		
		repository.save(cliente);
		
	}
	
	public Pedido findById(Long id) {
		
		return repository.findById(id).orElse(null);
		
	}
	
	public Iterable<Pedido> list() {
		
		return repository.findAll();
		
	}
	
	public void delete(Long id) {
		
		repository.deleteById(id);
		
	}

	public void addItem(Pedido pedido, ItemPedido item) {
		
		pedido.getItens().add(item);
		
		BigDecimal total = pedido.getItens().stream()
							.map(curItem -> curItem.getPreco().multiply(new BigDecimal(curItem.getQuantidade())))
								.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		pedido.setValor(total);
		
		item.setPedido(pedido);
		
		itemPedidoRepository.save(item);
		
		repository.save(pedido);
		
	}
	
}
