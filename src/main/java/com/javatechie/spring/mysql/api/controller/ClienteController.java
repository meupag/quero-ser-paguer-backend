package com.javatechie.spring.mysql.api.controller;

import java.util.List;
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
import com.javatechie.spring.mysql.api.dto.ClienteDTO;
import com.javatechie.spring.mysql.api.model.Cliente;
import com.javatechie.spring.mysql.api.service.ClienteService;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/cliente")
@Validated
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
		
	@PostMapping				@ApiOperation(value = "Cadastra um cliente")
	public ResponseEntity<?> addClientes(@RequestBody @Valid ClienteDTO clientesDto) {
		clienteService.addClientes(clientesDto);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	
	@GetMapping					@ApiOperation(value = "Consulta todos os clientes cadastrados")
	public @ResponseBody List<Cliente> getAllClientes() {
		return clienteService.getAllClientes(); 
	}
	
	@GetMapping("/{id}")		@ApiOperation(value = "Consulta cliente via id")
	public @ResponseBody Cliente getClienteById(@PathVariable Long id) {
		Cliente cliente = clienteService.getClienteById(id);
		return cliente;
	}
	
	@GetMapping("/cpf/{cpf}")	@ApiOperation(value = "Consulta cliente via CPF")
	public @ResponseBody Cliente getClienteByCpf(@PathVariable @Size(min=11, max=11, message = "CPF deve conter 11 dígitos") String cpf) {
		return clienteService.getClienteByCpf(cpf);
	}
	

	@DeleteMapping("/{id}")		@ApiOperation(value = "Deleta cliente")
	public ResponseEntity<?> delCliente(@PathVariable Long id) {
		clienteService.delCliente(id);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	@PutMapping("/{id}")		@ApiOperation(value = "Atualiza dados do cliente")
	public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteDTO clientesDto) {
		clienteService.updateCliente(id, clientesDto);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
}
