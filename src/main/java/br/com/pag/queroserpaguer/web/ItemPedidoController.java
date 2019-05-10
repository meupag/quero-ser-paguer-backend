package br.com.pag.queroserpaguer.web;


import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * Atualiza um 
	 * @param  com os dados atualizados
	 * @return Reponse OK se o  for atualizado e  
	 * 			Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = "Atualiza um item do pedido")
	@PutMapping
	public ResponseEntity<ItemPedido> updateItemPedido(@Valid @RequestBody ItemPedido entity)  {


		return itemPedidoService.findById(entity.getId())
				.map(itemPedido -> {
					itemPedido.setPreco(entity.getPreco());
					itemPedido.setQuantidade(entity.getQuantidade());
					itemPedido = itemPedidoService.save(itemPedido);
					return ResponseEntity.ok().body(itemPedido);
				}
						).orElse(ResponseEntity.notFound().build());

	}

	/**
	 * Busca todos os ItemPedido
	 * @return Lista de ItemPedido
	 */
	@ApiOperation(value = "Busca todos os ItemPedido")
	@RequestMapping
	public ResponseEntity<Collection<ItemPedido>> findAll() {
		List<ItemPedido> itensPedido = itemPedidoService.findAll();
		return ResponseEntity.ok(itensPedido);
	}

	/**
	 * Busca o itemPedido pelo Id
	 *
	 * @param id do 
	 * @return  response Ok se encontrar o 
	 *         ou Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = "Busca um item do pedido pelo Id")
	@GetMapping("/{id}")
	public ResponseEntity<ItemPedido> findById(@PathVariable Long id) {
		return  itemPedidoService.findById(id).map(itemPedido -> 
		ResponseEntity.ok().body(itemPedido)).orElse(ResponseEntity.notFound().build());
	}


	/**
	 *  c
	 *
	 * @param id do itemPedido a se apagado
	 * @return Response Ok se o  for apagado
	 * 		Reponse Not Found se o  não for encontrado.
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "apaga um item do pedidod")
	public ResponseEntity<?> deleteItemPedido(@PathVariable Long id) {
		return itemPedidoService.findById(id).map(item -> {
			itemPedidoService.delete(id);
			return ResponseEntity.ok().build();
		}
				).orElse(ResponseEntity.notFound().build());

	}
}
