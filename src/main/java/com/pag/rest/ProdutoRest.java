package com.pag.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.pag.db.repository.ProdutoRepository;
import com.pag.exceptions.RestUtilException;
import com.pag.modelo.Produto;
import com.pag.rest.utils.RestUtil;

@Component
public class ProdutoRest {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private  AmazonDynamoDB amazonDynamoDB;
	@Autowired
	private HttpServletRequest request;
	private Logger log = Logger.getLogger(ProdutoRest.class);
	private static final String PRODUTO_EXISTENTE = "Produto já existente na base de dados";
	private static final String PRODUTO_NAO_EXISTENTE = "Produto não existe na base de dados";
	private static final String NOME_TABELA = "produto";

	public ResponseEntity<String> getProdutos()throws RestUtilException{

		try {
			return RestUtil.getResponseByList((List<Produto>) produtoRepository.findAll());
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	private boolean hasProdutoByNome(String nome) {
		
		Map<String, AttributeValue> param = new HashMap<String, AttributeValue>();
		param.put(":nome", new AttributeValue().withS(nome.trim().toUpperCase())); 
		ScanRequest scanRequest = new ScanRequest().withTableName(NOME_TABELA) .withFilterExpression("nome = :nome")
									.withProjectionExpression("id").withExpressionAttributeValues(param);
		ScanResult result = amazonDynamoDB.scan(scanRequest);
		return result.getCount() > 0;
	}
	
	private boolean hasProdutoById(String id) {
		return produtoRepository.existsById(id);
	}
	
	public ResponseEntity<String> getById(String id) throws RestUtilException {
		
		try {
			return RestUtil.getResponseByOptional(produtoRepository.findById(id));
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> salvar(Produto produto) throws RestUtilException {

		try {
			
			if(!hasProdutoByNome(produto.getNome())) {
				produtoRepository.save(produto);
				return   ResponseEntity.status(HttpStatus.CREATED).header("location", request.getRequestURI() + produto.getId()).build();
			}else {
				return RestUtil.toResponseError(PRODUTO_EXISTENTE,HttpStatus.CONFLICT);
			}
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ResponseEntity<String> atualizar(Produto produto) throws RestUtilException {

		try {
			
			if(hasProdutoById(produto.getId())) {
				produtoRepository.save(produto);
				return new ResponseEntity<String>(HttpStatus.OK);
			}else {
				return RestUtil.toResponseError(PRODUTO_NAO_EXISTENTE,HttpStatus.CONFLICT);
			}
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> apagarProduto(String id) throws RestUtilException {
		
		try {
			produtoRepository.deleteById(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
