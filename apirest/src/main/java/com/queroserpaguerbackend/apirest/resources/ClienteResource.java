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

import com.queroserpaguerbackend.apirest.models.Cliente;
import com.queroserpaguerbackend.apirest.repository.ClienteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST MeuPag")
@CrossOrigin(origins="*")
public class ClienteResource {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping("/clientes")
	@ApiOperation(value="Retorna uma lista de Clientes")
	public List<Cliente> Clientes(){
		return clienteRepository.findAll();
	}
	
	@GetMapping(value="/cliente/{id}")
	@ApiOperation(value="Retorna um único Cliente")
	public Cliente getClientes(@PathVariable(value="id") long id) {
		return clienteRepository.findById(id);
	}
	
	@PostMapping(value="/cliente")
	@ApiOperation(value="Salva um Cliente")
	public Cliente postCliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@PutMapping(value="/cliente")
	@ApiOperation(value="Atualiza um Cliente")
	public Cliente updateCliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@DeleteMapping(value="/cliente")
	@ApiOperation(value="Deleta um Cliente")
	public void deleteCliente(@RequestBody Cliente cliente) {
		clienteRepository.delete(cliente);
	}
}
