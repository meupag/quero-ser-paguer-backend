package br.com.pag.pedido;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pag.exception.DataNotFoundException;
import br.com.pag.produto.Produto;
import br.com.pag.produto.ProdutoService;

@RestController
@RequestMapping(path = "/pedido", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping()
	public ResponseEntity<?> save(@RequestBody @Valid Pedido pedido) {
		
		pedido.setValor(BigDecimal.ZERO);
		
		pedidoService.save(pedido);
		
		return ResponseEntity.accepted().body(pedido);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> addItem(@PathVariable("id") Long id, @RequestBody @Valid ItemPedido item) {
		
		Pedido pedido = pedidoService.findById(id);
		
		if(pedido == null) {
			throw new DataNotFoundException("Pedido " + id);
		}
		
		Produto produto = produtoService.findById(item.getProduto().getId());
		
		if(produto == null) {
			throw new DataNotFoundException("Produto " + item.getProduto().getId());
		}
		
		pedidoService.addItem(pedido, item);
		
		return ResponseEntity.accepted().body(pedido);
	}
	
	@GetMapping()
	public Iterable<Pedido> list() {
		
		return pedidoService.list();
	}

	@GetMapping("/{id}")
	public Pedido get(@PathVariable Long id) {
		
		Pedido pedido = pedidoService.findById(id);
		
		if(pedido == null) {
			throw new DataNotFoundException("Pedido " + id);
		}
		
		return pedido;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		try {
			pedidoService.delete(id);
		} catch(EmptyResultDataAccessException e) {
			throw new DataNotFoundException("Pedido " + id);
		}
		
		return ResponseEntity.accepted().build();
	}

}
