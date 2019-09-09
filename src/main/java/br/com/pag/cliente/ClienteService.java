package br.com.pag.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public void save(Cliente cliente) {
		
		clienteRepository.save(cliente);
		
	}
	
	public Cliente findById(Long id) {
		
		return clienteRepository.findById(id).orElse(null);
		
	}
	
	public Iterable<Cliente> list() {
		
		return clienteRepository.findAll();
		
	}
	
	public void delete(Long id) {
		
		clienteRepository.deleteById(id);
		
	}
	
}
