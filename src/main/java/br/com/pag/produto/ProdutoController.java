package br.com.pag.produto;

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
@RequestMapping(path = "/produto", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@PostMapping()
	public ResponseEntity<?> save(@RequestBody @Valid Produto produto) {
		
		service.save(produto);
		
		return ResponseEntity.accepted().body(produto);
	}
	
	@GetMapping()
	public Iterable<Produto> list() {
		
		return service.list();
	}

	@GetMapping("/{id}")
	public Produto get(@PathVariable Long id) {
		
		Produto produto = service.findById(id);
		
		if(produto == null) {
			throw new DataNotFoundException("Produto " + id);
		}
		
		return produto;
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody @Valid Produto produto) {
		
		service.save(produto);
		
		return ResponseEntity.accepted().body(produto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		try {
			service.delete(id);
		} catch(EmptyResultDataAccessException e) {
			throw new DataNotFoundException("Produto " + id);
		}
		
		return ResponseEntity.accepted().build();
	}

}
