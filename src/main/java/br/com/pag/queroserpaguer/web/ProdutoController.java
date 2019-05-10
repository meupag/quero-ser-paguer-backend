package br.com.pag.queroserpaguer.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pag.queroserpaguer.domain.Produto;
import br.com.pag.queroserpaguer.service.ProdutoService;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link br.com.pag.domain.Produto}.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	/**
	 * Busca o produto pelo Id
	 *
	 * @param id do produto
	 * @return  response Ok se encontrar o produto
	 *         ou Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = "Busca o produto pelo Id")
	@GetMapping("/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Long id) {
		return  produtoService.findById(id).map(produto -> 
		ResponseEntity.ok().body(produto)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Cria um novo Produto
	 *
	 * @param produto 
	 * @return novo Produto criado.
	 *         
	 * @throws URISyntaxException 
	 * 
	 */
	@ApiOperation(value = "Cria um novo Produto")
	@PostMapping
	public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) throws URISyntaxException  {
		if (produto.getId() != null) {
			throw new RuntimeException("Não é possível salvar um produto que já possua um ID.");
		}
		produto =  produtoService.save(produto); 

		return ResponseEntity.created(new URI("/produtos/" +
				produto.getId())).body(produto);

	}



	/**
	 * Atualiza um produto
	 * @param produto com os dados atualizados
	 * @return Reponse OK se o produto for atualizado e  
	 * 			Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = " Atualiza um produto")
	@PutMapping
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto)  {
		return produtoService.findById(produto.getId())
				.map(updateProduto -> {
					updateProduto.setNome(produto.getNome());
					updateProduto.setPrecoSugerido(produto.getPrecoSugerido()	);
					updateProduto.setNome(produto.getNome());
					updateProduto = produtoService.save(updateProduto);
					return  ResponseEntity.ok().body( updateProduto ) ;
				}
						).orElse(ResponseEntity.notFound().build());

	}

	/**
	 * Busca todos os Produtos
	 * @return Lista de Produtos
	 */
	@ApiOperation(value = "Busca todos os Produtos ")
	@GetMapping
	public ResponseEntity<Collection<Produto>> findAll() {
		List<Produto> produtos =  produtoService.findAll();
		return ResponseEntity.ok(produtos);
	}

	/**
	 *  apaga um Produto
	 *
	 * @param id do Produto a se apagado
	 * @return Response Ok se o produto for apagado
	 * 		Reponse Not Found se o produto não for encontrado.
	 */
	@ApiOperation(value = "apaga um Produto")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduto(@PathVariable Long id) {

		return produtoService.findById(id)
				.map(produto -> {
					produtoService.delete(produto.getId());
					return ResponseEntity.ok().build();
				}
						).orElse(ResponseEntity.notFound().build());
	}
}
