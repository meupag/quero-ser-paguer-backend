package com.pag.controller;

import javax.servlet.ServletContext;
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

import com.pag.modelo.Cliente;
import com.pag.rest.ClienteRest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Cliente")
@RequestMapping("v1/clientes")
public class ClienteControler extends ValidatorBadRequest {

	@Autowired
	private ClienteRest clientRest;
	private Logger log = Logger.getLogger(ClienteControler.class);
	
	@Autowired
	ServletContext context;
	
	@ApiOperation(value = "Recuperar todos os clientes",code = 200, httpMethod = "GET",produces = "JSON",protocols = "HTTP")
	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> getClientes() {

		try {
			return clientRest.getClientes();
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Recuperar um cliente pelo CPF",code = 200, httpMethod = "GET",produces = "JSON",protocols = "HTTP")
	@GetMapping( path = "/{cpf}" )
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> getClientes(@PathVariable String cpf) {

		try {
			return clientRest.getByCPF(cpf);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Cadastrar um novo cliente",code = 201, httpMethod = "POST",consumes = "JSON",protocols = "HTTP")
	@PostMapping( path = "/" )
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> salvar(@Valid @RequestBody Cliente cliente){
		try {
			return clientRest.salvar(cliente);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@ApiOperation(value = "Atualizar um cliente",code = 200, httpMethod = "PUT",consumes = "JSON",protocols = "HTTP")
	@PutMapping( path = "/" )
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> atualizar(@Valid @RequestBody Cliente cliente){
		try {
			return clientRest.atualizar(cliente);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value="Excluir um cliente pelo CPF",code = 200, httpMethod="DELETE",protocols = "HTTP")
	@DeleteMapping( path = "/{cpf}" )
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> apagarCliente(@PathVariable String cpf) {

		try {
			return clientRest.apagarByCPF(cpf);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
