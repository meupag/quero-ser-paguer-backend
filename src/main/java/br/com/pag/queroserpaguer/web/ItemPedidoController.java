package br.com.pag.queroserpaguer.web;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

import br.com.pag.queroserpaguer.domain.ItemPedido;
import br.com.pag.queroserpaguer.domain.ItemPedido;
import br.com.pag.queroserpaguer.service.ItemPedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link br.com.pag.domain.ItemPedido}.
 */
@RestController
@RequestMapping("/item-pedidos")
@Api(value = "Item Pedido")
public class ItemPedidoController {


	private final ItemPedidoService itemPedidoService;

	public ItemPedidoController(ItemPedidoService itemPedidoService) {
		this.itemPedidoService = itemPedidoService;
	}


	
	/**
	 * Busca o itemPedido pelo Id
	 *
	 * @param id do itemPedido
	 * @return  response Ok se encontrar o itemPedido
	 *         ou Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = "Busca o itemPedido pelo Id")
	@GetMapping("/{id}")
	public ResponseEntity<ItemPedido> findById(@PathVariable Long id) {
		return itemPedidoService.findById(id).map(itemPedido -> ResponseEntity.ok().body(itemPedido)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Atualiza um itemPedido
	 * @param itemPedido com os dados atualizados
	 * @return Reponse OK se o itemPedido for atualizado e  
	 * 			Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = " Atualiza um itemPedido")
	@PutMapping("/{id}")
	public ResponseEntity<ItemPedido> updateItemPedido(@PathVariable Long id,@Valid @RequestBody ItemPedido itemPedido)  {
		Optional<ItemPedido> itemPedidoAtualizado = itemPedidoService.update(id, itemPedido); 
		if(itemPedidoAtualizado.isPresent())
			return ResponseEntity.ok().body( itemPedidoAtualizado.get() );
		else
			return ResponseEntity.notFound().build();

	}

	/**
	 * Busca todos os ItemPedidos
	 * @return Lista de ItemPedidos
	 */
	@ApiOperation(value = "Busca todos os ItemPedidos ")
	@GetMapping
	public ResponseEntity<Collection<ItemPedido>> findAll() {
		List<ItemPedido> itemPedidos =  itemPedidoService.findAll();
		return ResponseEntity.ok(itemPedidos);
	}

	/**
	 *  apaga um ItemPedido
	 *
	 * @param id do ItemPedido a se apagado
	 * @return Response Ok se o itemPedido for apagado
	 * 		Reponse Not Found se o itemPedido não for encontrado.
	 */
	@ApiOperation(value = "apaga um ItemPedido")
	@DeleteMapping("/{id}")
	public void deleteItemPedido(@PathVariable Long id) {
		itemPedidoService.delete(id);
	}
}
