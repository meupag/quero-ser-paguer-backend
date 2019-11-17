package com.queroserpaguerbackend.apirest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.queroserpaguerbackend.apirest.models.Pedido;
import com.queroserpaguerbackend.apirest.repository.PedidoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST MeuPag")
@CrossOrigin(origins="*")
public class PedidoResource {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@GetMapping("/pedidos")
	@ApiOperation(value="Retorna uma lista de Pedidos")
	public List<Pedido> pedidos(){
		return pedidoRepository.findAll();
	}
	
	@GetMapping(value="/pedido/{id}")
	@ApiOperation(value="Retorna um único Pedido")
	public Pedido getPedidos(@PathVariable(value="id") long id) {
		return pedidoRepository.findById(id);
	}
	
	@PostMapping(value="/pedido")
	@ApiOperation(value="Salva um Pedido")
	public Pedido postPedido(@RequestBody Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	@PutMapping(value="/pedido")
	@ApiOperation(value="Atualiza um Pedido")
	public Pedido updatePedido(@RequestBody Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@DeleteMapping(value="/pedido")
	@ApiOperation(value="Deleta um Pedido")
	public void deletePedido(@RequestBody Pedido pedido) {
		pedidoRepository.delete(pedido);
	}
}

