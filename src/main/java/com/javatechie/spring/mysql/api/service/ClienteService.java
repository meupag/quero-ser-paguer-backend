package com.javatechie.spring.mysql.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javatechie.spring.mysql.api.dto.ClienteDTO;
import com.javatechie.spring.mysql.api.exception.ExistingObjectException;
import com.javatechie.spring.mysql.api.exception.RecordNotFoundException;
import com.javatechie.spring.mysql.api.model.Cliente;
import com.javatechie.spring.mysql.api.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	
	public void addClientes(ClienteDTO clienteDto) {
		Cliente cliente = new Cliente(clienteDto);
		if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
			throw new ExistingObjectException("Cliente já existente com este CPF");
		}
		
		clienteRepository.save(cliente);
	}
	
	
	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll(); 
	}
	
	
	public Cliente getClienteById(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			return cliente.get();
		}
		else {
			throw new RecordNotFoundException("Cliente não encontrado.");
		}
	}
	
	
	public Cliente getClienteByCpf(String cpf) {
		Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);
		if (cliente.isPresent()) {
			return cliente.get();
		}
		else {
			throw new RecordNotFoundException("Cliente não encontrado.");
		}
	}
	
	
	public void delCliente(Long id) {
		Optional<Cliente> clienteDto  = clienteRepository.findById(id);
		if (clienteDto.isPresent()) {
			clienteRepository.delete(clienteDto.get());
		}
		else {
			throw new RecordNotFoundException("Cliente não encontrado.");
		}
	}
	
	public void updateCliente(Long id, ClienteDTO clientesDto) {
		Optional<Cliente> clienteOpt = clienteRepository.findById(id);
		if (clienteOpt.isPresent()) {
			Cliente cliente = new Cliente(clientesDto);
			cliente.setId(id);
			clienteRepository.save(cliente);
		}
		else {
			throw new RecordNotFoundException("Cliente não encontrado.");
		}
	}
}
