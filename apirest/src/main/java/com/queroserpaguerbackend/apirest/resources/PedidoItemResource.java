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

import com.queroserpaguerbackend.apirest.models.PedidoItem;
import com.queroserpaguerbackend.apirest.repository.PedidoItemRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST MeuPag")
@CrossOrigin(origins="*")
public class PedidoItemResource {
	
	@Autowired
	PedidoItemRepository pedidoItemRepository;
	
	@GetMapping("/pedidoItems")
	@ApiOperation(value="Retorna uma lista de PedidoItems")
	public List<PedidoItem> pedidoItems(){
		return pedidoItemRepository.findAll();
	}
	
	@GetMapping(value="/pedidoItem/{id}")
	@ApiOperation(value="Retorna um único PedidoItem")
	public PedidoItem getPedidoItems(@PathVariable(value="id") long id) {
		return pedidoItemRepository.findById(id);
	}
	
	@PostMapping(value="/pedidoItem")
	@ApiOperation(value="Salva um PedidoItem")
	public PedidoItem postPedidoItem(@RequestBody PedidoItem pedidoItem) {
		return pedidoItemRepository.save(pedidoItem);
	}
	
	@PutMapping(value="/pedidoItem")
	@ApiOperation(value="Atualiza um PedidoItem")
	public PedidoItem updatePedidoItem(@RequestBody PedidoItem pedidoItem) {
		return pedidoItemRepository.save(pedidoItem);
	}

	@DeleteMapping(value="/pedidoItem")
	@ApiOperation(value="Deleta um PedidoItem")
	public void deletePedidoItem(@RequestBody PedidoItem pedidoItem) {
		pedidoItemRepository.delete(pedidoItem);
	}
}

