package com.pag.controller;

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

import com.pag.modelo.Produto;
import com.pag.rest.ProdutoRest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="produtos")
@RequestMapping("v1/produtos")
public class ProdutoController extends ValidatorBadRequest {

	@Autowired
	private ProdutoRest produtoRest;
	private Logger log = Logger.getLogger(ProdutoController.class);
	
	
	@ApiOperation(value = "Recuperar todos os produtos",code = 200, httpMethod = "GET",produces = "JSON",protocols = "HTTP")
	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> getClientes() {

		try {
			return produtoRest.getProdutos();
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Recuperar um produto por seu identificador",code = 200, httpMethod = "GET",produces = "JSON",protocols = "HTTP")
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public @ResponseBody ResponseEntity<String> getClientes(@PathVariable String id) {

		try {
			return produtoRest.getById(id);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@ApiOperation(value = "Cadastrar um produto",code = 201, httpMethod = "POST",consumes = "JSON",protocols = "HTTP")
	@PostMapping("/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> salvar(@Valid @RequestBody Produto produto){
		try {
			return produtoRest.salvar(produto);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Atualizar um produto",code = 200, httpMethod = "PUT",consumes= "JSON",protocols = "HTTP")
	@PutMapping("/")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> atualizar(@Valid  @RequestBody Produto produto){
		try {
			return produtoRest.atualizar(produto);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ApiOperation(value = "Apagar um produto pelo seu identificador",code = 200, httpMethod = "GET",protocols = "HTTP")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> apagarProduto(@PathVariable String id) {

		try {
			return produtoRest.apagarProduto(id);
		} catch (Exception e) {
			log.error(e,e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
