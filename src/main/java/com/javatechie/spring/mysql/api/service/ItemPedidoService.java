package com.javatechie.spring.mysql.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatechie.spring.mysql.api.exception.RecordNotFoundException;
import com.javatechie.spring.mysql.api.model.ItemPedido;
import com.javatechie.spring.mysql.api.model.Pedido;
import com.javatechie.spring.mysql.api.repository.ItemPedidoRepository;
import com.javatechie.spring.mysql.api.repository.PedidoRepository;
import com.javatechie.spring.mysql.api.repository.ProdutoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	
    public List<ItemPedido> getAllItens() {
        return itemPedidoRepository.findAll();
    }
    
    public ItemPedido getItemById(Long id) {
    	Optional<ItemPedido> itemOpt = itemPedidoRepository.findById(id); 
    	if (itemOpt.isPresent()) {
			return itemOpt.get();
		}
		else {
			throw new RecordNotFoundException("Item não encontrado");
		}
    }
	
    
	public List<ItemPedido> getItensByPedidoId(Long pedidoId) {
		if (pedidoRepository.findById(pedidoId).isPresent()) {
			List<ItemPedido> itens = new ArrayList<>();
			itemPedidoRepository.findByPedidoId(pedidoId).forEach(itens::add);
			return itens;
		}
		else {
			throw new RecordNotFoundException("Pedido não encontrado");
		}
	}
	
	
	public void delItemPedido(Long id) {
		Optional<ItemPedido> itemOpt = itemPedidoRepository.findById(id);
		
		if (itemOpt.isPresent()) {
			ItemPedido item = itemOpt.get();
			itemPedidoRepository.delete(item);
			
			Pedido pedido = pedidoRepository.findById(item.getIdPedido()).get();
			
			BigDecimal valorAtual = pedido.getValor().subtract(item.getPreco().multiply(item.getQuantidade()));
			
			if (pedido.getItensPedido().isEmpty()) {
				pedidoRepository.delete(pedido);
			}
			else {
				pedidoRepository.updateValor(valorAtual, pedido.getId());
			}
		}
		else {
			throw new RecordNotFoundException("Item não encontrado");
		}
	}
}
