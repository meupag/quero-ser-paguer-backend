package com.pag.controller;

import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pag.modelo.Item;
import com.pag.modelo.Pedido;
import com.pag.rest.PedidoRest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "pedidos")
@RestController
@RequestMapping("v1/pedidos")
public class PedidoController extends ValidatorBadRequest{


	@Autowired
	private PedidoRest pedidoRest;
	
	private Logger log = Logger.getLogger(PedidoController.class);
	
	@ApiOperation(value = "Recuperar um pedido pelo cliente e identicador do pedido",code = 200, httpMethod = "GET",produces = "JSON",protocols = "HTTP")
	@GetMapping("/{cpf}/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> getPedido(@PathVariable String cpf,@PathVariable String id) {

		try {
			return pedidoRest.getById(id,cpf);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Recuperar uma lista de pedidos do cliente",code = 200, httpMethod = "GET",produces = "JSON",protocols = "HTTP")
	@GetMapping("/{cpf}/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> getPedidos(@PathVariable String cpf) {

		try {
			return pedidoRest.getByCPF(cpf);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Gravar um pedido do cliente",code = 201, httpMethod = "POST",consumes = "JSON",protocols = "HTTP")
	@PostMapping("/{cpf}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> salvar(@Valid @RequestBody List<Item> itens,@PathVariable String cpf){
		try {
			return pedidoRest.salvar(cpf,itens);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Modificar um pedido do cliente",code = 200, httpMethod = "PUT",consumes = "JSON",protocols = "HTTP")
	@PutMapping("/{cpf}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> atualizar(@Valid @RequestBody Pedido pedido, @PathVariable String cpf){
		try {
			pedido.setCliente(cpf);
			return pedidoRest.atualizar(pedido);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Apagar um pedido do cliente",code = 200, httpMethod = "DELETE",protocols = "HTTP")
	@DeleteMapping("/{cpf}/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> apagarPedido(@PathVariable String cpf, @PathVariable String id) {

		try {
			return pedidoRest.apagarByIdAndCPF(id, cpf);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
