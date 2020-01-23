package com.javatechie.spring.mysql.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;

import com.javatechie.spring.mysql.api.dto.ProdutoDTO;
import com.javatechie.spring.mysql.api.exception.ExistingObjectException;
import com.javatechie.spring.mysql.api.exception.RecordNotFoundException;
import com.javatechie.spring.mysql.api.model.Produto;
import com.javatechie.spring.mysql.api.model.Produto;
import com.javatechie.spring.mysql.api.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
    ProdutoRepository produtoRepository;
	

	public void addProdutos(ProdutoDTO produtoDto) {
		Produto produto = new Produto(produtoDto);
		if (produtoRepository.findByNome(produto.getNome()).isPresent()) {
			throw new ExistingObjectException("Produto já existente com este nome");
		}
		produtoRepository.save(produto);
	}
	
	
	public List<Produto> getAllProdutos() {
		return produtoRepository.findAll();
	}
	
	
	public Produto getProdutoById(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			return produto.get();
		}
		else {
			throw new RecordNotFoundException("Produto não encontrado.");
		}
	}
	
	
	public void delProduto(Long id) {
		Optional<Produto> produtoDto = produtoRepository.findById(id);
		if (produtoDto.isPresent()) {
			produtoRepository.delete(produtoDto.get());
		}
		else {
			throw new RecordNotFoundException("Produto não encontrado.");
		}
	}
	
	
	public void updateProduto(Long id, ProdutoDTO produtoDto) {
		Optional<Produto> produtoOpt = produtoRepository.findById(id);
		if (produtoOpt.isPresent()) {
			Produto produto = new Produto(produtoDto);
			produto.setId(id);
			produtoRepository.save(produto);
		}
		else {
			throw new RecordNotFoundException("Produto não encontrado.");
		}
	}
	
	
}
