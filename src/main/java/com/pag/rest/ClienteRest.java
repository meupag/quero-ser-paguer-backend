package com.pag.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pag.db.repository.ClienteRepository;
import com.pag.exceptions.RestUtilException;
import com.pag.modelo.Cliente;
import com.pag.rest.utils.RestUtil;

@Component
public class ClienteRest  {


	@Autowired
	private  ClienteRepository clienteRepository;
	@Autowired
	private HttpServletRequest request;
	private Logger log = Logger.getLogger(ClienteRest.class);
	private static final String CLIENTE_EXISTENTE = "CPF existente na base de dados";
	private static final String CLIENTE_NAO_EXISTENTE = "CPF não existe na base de dados";
	

	public ResponseEntity<String> getClientes()throws RestUtilException{

		try {
			return RestUtil.getResponseByList((List<Cliente>) clienteRepository.findAll());
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean hasCliente(String cpf) {
		return clienteRepository.existsById(cpf);
	}
	
	public ResponseEntity<String> getByCPF(String cpf) throws RestUtilException {
		
		try {
			return RestUtil.getResponseByOptional(clienteRepository.findById(cpf));
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	public ResponseEntity<String> salvar(Cliente cliente) throws RestUtilException {

		try {
			if(!hasCliente(cliente.getCpf())) {
				clienteRepository.save(cliente);
				return ResponseEntity.status(HttpStatus.CREATED).header("location", request.getRequestURI() + cliente.getCpf()).build();
			}else {
				return RestUtil.toResponseError(CLIENTE_EXISTENTE,HttpStatus.CONFLICT);
			}
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> atualizar(Cliente cliente) throws RestUtilException {

		try {
			if(hasCliente(cliente.getCpf())) {
				clienteRepository.save(cliente);
				return new ResponseEntity<String>(HttpStatus.OK);
			}else {
				return RestUtil.toResponseError(CLIENTE_NAO_EXISTENTE,HttpStatus.CONFLICT);
			}
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> apagarByCPF(String cpf) throws RestUtilException {
		
		try {
			clienteRepository.deleteById(cpf);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (Exception e) {
			log.error(e,e);
			return RestUtil.toResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
