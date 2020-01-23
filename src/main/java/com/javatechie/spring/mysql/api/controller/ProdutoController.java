package com.javatechie.spring.mysql.api.controller;

import java.util.List;
import javax.validation.Valid;
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
import com.javatechie.spring.mysql.api.dto.ProdutoDTO;
import com.javatechie.spring.mysql.api.model.Produto;
import com.javatechie.spring.mysql.api.service.ProdutoService;
//import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {
	
    @Autowired
    ProdutoService produtoService;
    
	
	@PostMapping				//@ApiOperation(value = "Cadastra um produto")
	public ResponseEntity<?> addProdutos(@RequestBody @Valid ProdutoDTO produtoDTO) {
		produtoService.addProdutos(produtoDTO);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	
	@GetMapping					//@ApiOperation(value = "Consulta todos os produtos cadastrados")
	public @ResponseBody List<Produto> getAllProdutos() {
		return produtoService.getAllProdutos();
	}
	
	@GetMapping("/{id}")		//@ApiOperation(value = "Consulta produto via id")
	public @ResponseBody Produto getProdutoById(@PathVariable Long id) {
		return produtoService.getProdutoById(id);
	}
	
	
	@DeleteMapping("/{id}")		//@ApiOperation(value = "Deleta produto")
	public ResponseEntity<?> delProduto(@PathVariable Long id) {
		produtoService.delProduto(id);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	@PutMapping("/{id}")		//@ApiOperation(value = "Atualiza dados do produto")
	public  ResponseEntity<?> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoDTO produtoDto) {
		produtoService.updateProduto(id, produtoDto);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	


}
