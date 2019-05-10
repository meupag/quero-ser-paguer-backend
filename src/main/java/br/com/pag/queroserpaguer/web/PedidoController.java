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

import br.com.pag.queroserpaguer.domain.Pedido;
import br.com.pag.queroserpaguer.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link br.com.pag.domain.Pedido}.
 */
@RestController
@RequestMapping("/pedidos")
@Api(value = "Pedido")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	/**
	 * Cria um novo pedido
	 *
	 * @param pedido 
	 * @return novo pedido criado.
	 *         
	 * @throws URISyntaxException 
	 * 
	 */
	@PostMapping
	@ApiOperation(value = "Cria um novo pedido com seus itens, o valor será calculado a partir da soma da multiplicação da quantidade pelo valor dos itens do pedido. ")
	public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) throws URISyntaxException {
		if (pedido.getId() != null) {
			throw new RuntimeException("Não é possível salvar um pedido que já possua um ID.");
		}
		pedido =   pedidoService.save(pedido);

		return ResponseEntity.created(new URI("/clientes/" +
				pedido.getId())).body(pedido);
	}

	/**
	 * Atualiza um pedido
	 * @param  com os dados atualizados
	 * @return Reponse OK se o  for atualizado e  
	 * 			Not Found se o Ciente não existir.
	 */
	@PutMapping
	@ApiOperation(value = "Atualiza um pedido com seus itens, o valor será recalculado a partir da soma da multiplicação da quantidade pelo valor dos itens do pedido. ")
	public ResponseEntity<Pedido> updatePedido(@Valid @RequestBody Pedido entity)  {


		return pedidoService.findById(entity.getId())
				.map(pedido -> {
					pedido.setItensPedido(entity.getItensPedido());
					pedido = pedidoService.save(pedido);
					return ResponseEntity.ok().body(pedido);
				}
						).orElse(ResponseEntity.notFound().build());

	}

	/**
	 * Busca todos os Pedido
	 * @return Lista de Pedido
	 */
	@ApiOperation(value = "Busca todos os Pedido.")
	@RequestMapping
	public ResponseEntity<Collection<Pedido>> findAll() {
		List<Pedido> pedidos = pedidoService.findAll();
		return ResponseEntity.ok(pedidos);
	}

	/**
	 * Busca o pedido pelo Id
	 *
	 * @param id do 
	 * @return  response Ok se encontrar o 
	 *         ou Not Found se o Ciente não existir.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable Long id) {
		return  pedidoService.findById(id).map(pedido -> 
		ResponseEntity.ok().body(pedido)).orElse(ResponseEntity.notFound().build());
	}


	/**
	 *  apaga um pedido
	 *
	 * @param id do pedido a se apagado
	 * @return Response Ok se o  for apagado
	 * 		Reponse Not Found se o  não for encontrado.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePedido(@PathVariable Long id) {
		return pedidoService.findById(id).map(item -> {
			pedidoService.delete(id);
			return ResponseEntity.ok().build();
		}
				).orElse(ResponseEntity.notFound().build());

	}
}
