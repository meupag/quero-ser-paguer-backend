package com.javatechie.spring.mysql.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.spring.mysql.api.exception.RecordNotFoundException;
import com.javatechie.spring.mysql.api.integration.RequestPedido;
import com.javatechie.spring.mysql.api.integration.RequestProduto;
import com.javatechie.spring.mysql.api.model.Cliente;
import com.javatechie.spring.mysql.api.model.ItemPedido;
import com.javatechie.spring.mysql.api.model.Pedido;
import com.javatechie.spring.mysql.api.model.Produto;
import com.javatechie.spring.mysql.api.repository.ItemPedidoRepository;
import com.javatechie.spring.mysql.api.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	
	public void addPedido (RequestPedido request) {
		Cliente cliente = clienteService.getClienteByCpf(request.getCpf());
		
		if (cliente != null) {
			Pedido pedido = new Pedido();
			pedido.setIdCliente(cliente.getId());
			pedido.setValor(request.getValor());
			pedidoRepository.save(pedido);
			
			for (RequestProduto item : request.getProdutos()) {				
				Produto produto = produtoService.getProdutoById(item.getIdProduto());
				if (produto != null) {
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setPreco(item.getPreco());
					itemPedido.setQuantidade(item.getQuantidade());
					itemPedido.setIdProduto(produto.getId());
					itemPedido.setIdPedido(pedido.getId());
					itemPedidoRepository.save(itemPedido);
				} else {
					throw new RecordNotFoundException("Produto não encontrado (Id: "+item.getIdProduto()+")");
				}
			}
		} else {
			throw new RecordNotFoundException("Cliente não encontrado (CPF: "+request.getCpf()+")");
		}
	}
	
	
	public List<Pedido> getAllPedidos() {
		return pedidoRepository.findAll();
	}
	
	
	public List<Pedido> getAllPedidosByCliente(String cpfCliente) {
		clienteService.getClienteByCpf(cpfCliente);
		return pedidoRepository.findByClienteCpf(cpfCliente);
	}
	
	
	public Pedido getPedidoById(Long id) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
		
		if (pedidoOpt.isPresent()) {
			Pedido pedido = pedidoOpt.get();
			List<ItemPedido> itensPedido = new ArrayList<>();
			itemPedidoRepository.findByPedidoId(pedido.getId()).forEach(itensPedido::add);
			
			pedido.setItensPedido(itensPedido);
			return pedido;
		}
		else {
			throw new RecordNotFoundException("Pedido não encontrado.");
		}
		
	}
	
	public void delPedido(Long id) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
				
		if (pedidoOpt.isPresent()) {
			Pedido pedido = pedidoOpt.get();
			pedidoRepository.delete(pedido);		
		}
		else {
			throw new RecordNotFoundException("Pedido não encontrado.");
		}
	}
	
	
	public void updatePedido(Long idPedido, RequestPedido request) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
		if (pedidoOpt.isPresent()) {
			Pedido pedido = pedidoOpt.get();
			pedido.setValor(request.getValor());
			pedidoRepository.save(pedido);
			
			for (RequestProduto itemRequest : request.getProdutos()) {	
				ItemPedido item = itemPedidoRepository.findByPedidoProduto(idPedido, itemRequest.getIdProduto());
				
				if (item != null) {
					item.setQuantidade(itemRequest.getQuantidade());
					item.setPreco(itemRequest.getPreco());
				}
				else {
					Produto produto = produtoService.getProdutoById(itemRequest.getIdProduto());
					if (produto != null) {
						item = new ItemPedido();
						item.setIdPedido(idPedido);
						item.setIdProduto(itemRequest.getIdProduto());
						item.setQuantidade(itemRequest.getQuantidade());
						item.setPreco(itemRequest.getPreco());
					}
					else {
						throw new RecordNotFoundException("Produto não encontrado (Id: "+itemRequest.getIdProduto()+")");
					}
				}
				itemPedidoRepository.save(item);
			}
		}
		else {
			throw new RecordNotFoundException("Pedido não encontrado.");
		}
		
	}
	
}
