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

import br.com.pag.queroserpaguer.domain.Cliente;
import br.com.pag.queroserpaguer.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clientes")
@Api(value = "Cliente")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	/**
	 * Busca o cliente pelo Id
	 *
	 * @param id do cliente
	 * @return  response Ok se encontrar o cliente
	 *         ou Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = "Busca o cliente pelo Id")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {
		return  clienteService.findById(id).map(cliente -> 
		ResponseEntity.ok().body(cliente)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Cria um novo Cliente
	 *
	 * @param cliente 
	 * @return novo Cliente criado.
	 *         
	 * @throws URISyntaxException 
	 * 
	 */
	@ApiOperation(value = "Cria um novo Cliente")
	@PostMapping
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) throws URISyntaxException  {
		if (cliente.getId() != null) {
			throw new RuntimeException("Não é possível salvar um cliente que já possua um ID.");
		}
		cliente =  clienteService.save(cliente); 

		return ResponseEntity.created(new URI("/clientes/" +
				cliente.getId())).body(cliente);

	}



	/**
	 * Atualiza um cliente
	 * @param cliente com os dados atualizados
	 * @return Reponse OK se o cliente for atualizado e  
	 * 			Not Found se o Ciente não existir.
	 */
	@ApiOperation(value = " Atualiza um cliente")
	@PutMapping
	public ResponseEntity<Cliente> updateCliente(@Valid @RequestBody Cliente cliente)  {
		return clienteService.findById(cliente.getId())
				.map(updateCliente -> {
					updateCliente.setCpf(cliente.getCpf());
					updateCliente.setDataNascimento(cliente.getDataNascimento());
					updateCliente.setNome(cliente.getNome());
					updateCliente = clienteService.save(updateCliente);
					return  ResponseEntity.ok().body( updateCliente ) ;
				}
						).orElse(ResponseEntity.notFound().build());

	}

	/**
	 * Busca todos os Clientes
	 * @return Lista de Clientes
	 */
	@ApiOperation(value = "Busca todos os Clientes ")
	@GetMapping
	public ResponseEntity<Collection<Cliente>> findAll() {
		List<Cliente> clientes =  clienteService.findAll();
		return ResponseEntity.ok(clientes);
	}

	/**
	 *  apaga um Cliente
	 *
	 * @param id do Cliente a se apagado
	 * @return Response Ok se o cliente for apagado
	 * 		Reponse Not Found se o cliente não for encontrado.
	 */
	@ApiOperation(value = "apaga um Cliente")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCliente(@PathVariable Long id) {

		return clienteService.findById(id)
				.map(cliente -> {
					clienteService.delete(cliente.getId());
					return ResponseEntity.ok().build();
				}
						).orElse(ResponseEntity.notFound().build());
	}

}
