package com.javatechie.spring.mysql.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.mysql.api.integration.RequestPedido;
import com.javatechie.spring.mysql.api.model.Pedido;
import com.javatechie.spring.mysql.api.service.PedidoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pedido")
@Validated
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping						@ApiOperation(value = "Cadastra um pedido com seus itens")
	public ResponseEntity<?> addPedido(@RequestBody @Valid RequestPedido request) {
		pedidoService.addPedido(request);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	
	@GetMapping							@ApiOperation(value = "Consulta todos os pedidos")
	public @ResponseBody List<Pedido> getAllPedidos() {
		return pedidoService.getAllPedidos();
	}
	
	@GetMapping("/{id}")				@ApiOperation(value = "Consulta pedido via id do pedido")
	public @ResponseBody Pedido getPedidoById(@PathVariable Long id) {
		return pedidoService.getPedidoById(id);
	}
	
	@GetMapping("/cpf/{cpfCliente}")	@ApiOperation(value = "Consulta pedidos de um cliente via CPF do cliente")
	public @ResponseBody List<Pedido> getAllPedidosByCliente(@PathVariable @Size(min=11, max=11, message = "CPF deve conter 11 dígitos") String cpfCliente) {
		return pedidoService.getAllPedidosByCliente(cpfCliente);
	}
	
	
	@DeleteMapping("/{id}")				@ApiOperation(value = "Deleta pedido e seus itens")
	public ResponseEntity<?> delPedido(@PathVariable Long id) {
		pedidoService.delPedido(id);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	@PutMapping("/{idPedido}")			@ApiOperation(value = "Atualiza pedido e seus itens")
	public ResponseEntity<?> updatePedido(@PathVariable Long idPedido, @RequestBody @Valid RequestPedido request) {
		pedidoService.updatePedido(idPedido, request);
		return new ResponseEntity<Void>( HttpStatus.OK );
		
	}
	
	
}
