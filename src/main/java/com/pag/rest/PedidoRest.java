package com.pag.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.pag.db.repository.PedidoRepository;
import com.pag.db.repository.ProdutoRepository;
import com.pag.exceptions.RestUtilException;
import com.pag.modelo.Item;
import com.pag.modelo.Pedido;
import com.pag.modelo.PedidoId;
import com.pag.modelo.Produto;
import com.pag.rest.utils.RestUtil;

@Component
public class PedidoRest  {



	@Autowired
	private  PedidoRepository pedidoRepository;
	@Autowired
	private  ProdutoRepository produtoRepository;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private DynamoDBMapper mapper;

	
	private Logger log = Logger.getLogger(PedidoRest.class);
	private static final String PEDIDO_NAO_EXISTENTE = "Pedido não encontrado na base de dados";
	private static final String PRODUTO_NAO_ENCONTRADO = "Pelo menos um dos produtos não está mais disponível";

	public ResponseEntity<String> getById(String id, String cpf)throws RestUtilException{

		try {
			PedidoId pedidoId = new PedidoId();
			pedidoId.setId(id);
			pedidoId.setIdCliente(cpf);
			return RestUtil.getResponseByOptional(pedidoRepository.findById(pedidoId));
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean hasPedido(PedidoId pedidoId) {
		return pedidoRepository.existsById(pedidoId);
	}
	
	public ResponseEntity<String> getByCPF(String cpf) throws RestUtilException {
		
		try {

			Map<String, AttributeValue> mapAtributos = new TreeMap<String, AttributeValue>();
			mapAtributos.put(":id_cliente", new AttributeValue(cpf));

			DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
			dbScanExpression.withFilterExpression("id_cliente = :id_cliente").withExpressionAttributeValues(mapAtributos);
			return RestUtil.getResponseByList(mapper.scan(Pedido.class, dbScanExpression));
		
		
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> salvar(String cpf,List<Item> itens) throws RestUtilException {

		try {
			
			return salvarOuInfomarErro(getNovoPedido(itens,cpf, null));
			
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> salvarOuInfomarErro(Pedido novoPedido) throws RestUtilException{
		
		if(novoPedido == null) {
			return RestUtil.toResponseError(PRODUTO_NAO_ENCONTRADO, HttpStatus.CONFLICT);
		}else {
			pedidoRepository.save(novoPedido);
			return  ResponseEntity.status(HttpStatus.CREATED).header("location", request.getRequestURI()  + novoPedido.getId()).build();
		}
	}
	
	private boolean hasProduto(Optional<Produto> optional) {
		return optional.isPresent();
	}
	
	private Pedido getNovoPedido(List<Item> itens,String cpf, String id) {
		
		Pedido novoPedido = new Pedido();
		Item novoItem = null;
		Produto produto = null;
		List<Item> itensNovos = new ArrayList<Item>();
		Double valor = 0.0;
		Optional<Produto> produtoOptional = null;
		boolean pedidoOk = true;
		for(Item item : itens) {
			
			produtoOptional = produtoRepository.findById(item.getId());
			
			if(hasProduto(produtoOptional)) {
				produto = produtoOptional.get();
				novoItem = new Item();
				novoItem.setId(item.getId());
				novoItem.setQuantidade(item.getQuantidade());
				novoItem.setPreco(produto.getPrecoSugerido());
				novoItem.setNome(produto.getNome());
				valor = (item.getQuantidade() * produto.getPrecoSugerido()) + valor;
				itensNovos.add(novoItem);
			}else {
				pedidoOk = false;
				break;
			}
		}
		
		if(pedidoOk) {
			if(id != null) {
				novoPedido.setId(id);
			}
			novoPedido.setCliente(cpf);
			novoPedido.setValor(valor);
			novoPedido.setItens(itensNovos);
			return novoPedido;
		}else {
			return null;
		}
		
	}
	
	public ResponseEntity<String> atualizar(Pedido pedido) throws RestUtilException {

		try {

			if(hasPedido(new PedidoId(pedido.getId(),pedido.getCliente()))) {
				
				Pedido novoPedido = getNovoPedido(pedido.getItens(), pedido.getCliente(), pedido.getId());
				return salvarOuInfomarErro(novoPedido);
				
			}else {
				return RestUtil.toResponseError(PEDIDO_NAO_EXISTENTE,HttpStatus.CONFLICT);
			}
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	public ResponseEntity<String> apagarByIdAndCPF(String id, String cpf) throws RestUtilException {
		
		try {
			pedidoRepository.deleteById(new PedidoId(id, cpf));
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
