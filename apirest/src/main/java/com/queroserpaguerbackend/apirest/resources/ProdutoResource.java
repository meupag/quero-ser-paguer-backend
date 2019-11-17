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

import com.queroserpaguerbackend.apirest.models.Produto;
import com.queroserpaguerbackend.apirest.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST MeuPag")
@CrossOrigin(origins="*")
public class ProdutoResource {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping("/produtos")
	@ApiOperation(value="Retorna uma lista de Produtos")
	public List<Produto> listaProdutos(){
		return produtoRepository.findAll();
	}
	
	@GetMapping(value="/produto/{id}")
	@ApiOperation(value="Retorna um único Produto")
	public Produto getProdutos(@PathVariable(value="id") long id) {
		return produtoRepository.findById(id);
	}
	
	@PostMapping(value="/produto")
	@ApiOperation(value="Salva um Produto")
	public Produto postProduto(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
	
	@PutMapping(value="/produto")
	@ApiOperation(value="Atualiza um Produto")
	public Produto updateProduto(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}

	@DeleteMapping(value="/produto")
	@ApiOperation(value="Deleta um Produto")
	public void deleteProduto(@RequestBody Produto produto) {
		produtoRepository.delete(produto);
	}
}
