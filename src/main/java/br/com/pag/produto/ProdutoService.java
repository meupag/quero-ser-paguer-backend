package br.com.pag.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public void save(Produto cliente) {
		
		repository.save(cliente);
		
	}
	
	public Produto findById(Long id) {
		
		return repository.findById(id).orElse(null);
		
	}
	
	public Iterable<Produto> list() {
		
		return repository.findAll();
		
	}
	
	public void delete(Long id) {
		
		repository.deleteById(id);
		
	}
	
}
