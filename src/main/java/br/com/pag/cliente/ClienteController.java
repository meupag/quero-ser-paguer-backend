package br.com.pag.cliente;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pag.exception.DataNotFoundException;

@RestController
@RequestMapping(path = "/cliente", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping()
	public ResponseEntity<?> save(@RequestBody @Valid Cliente cliente) {
		
		service.save(cliente);
		
		return ResponseEntity.accepted().body(cliente);
	}
	
	@GetMapping()
	public Iterable<Cliente> list() {
		
		return service.list();
	}

	@GetMapping("/{id}")
	public Cliente get(@PathVariable Long id) {
		
		Cliente cliente = service.findById(id);
		
		if(cliente == null) {
			throw new DataNotFoundException("Cliente " + id);
		}
		
		return cliente;
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody @Valid Cliente cliente) {
		
		System.out.println(cliente);
		
		service.save(cliente);
		
		return ResponseEntity.accepted().body(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		try {
			service.delete(id);
		} catch(EmptyResultDataAccessException e) {
			throw new DataNotFoundException("Cliente " + id);
		}
		
		return ResponseEntity.accepted().build();
	}

}
