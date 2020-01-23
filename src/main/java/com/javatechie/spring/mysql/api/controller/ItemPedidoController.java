package com.javatechie.spring.mysql.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.javatechie.spring.mysql.api.model.ItemPedido;
import com.javatechie.spring.mysql.api.service.ItemPedidoService;
//import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/item_pedido")
@Validated
public class ItemPedidoController {

	@Autowired
    private ItemPedidoService itemPedidoService;
	
    @GetMapping							//@ApiOperation(value = "Consulta todos os itens da base")
    public @ResponseBody List<ItemPedido> getAllItens() {
        return itemPedidoService.getAllItens();
    }
	
	@GetMapping("/{id}")				//@ApiOperation(value = "Consulta item via id")
	public @ResponseBody ItemPedido getItemById(@PathVariable Long id) {
		return itemPedidoService.getItemById(id);
	}
    
	@GetMapping("/pedido/{pedidoId}")	//@ApiOperation(value = "Consulta itens de um pedido")
	public @ResponseBody List<ItemPedido> getItensByPedidoId(@PathVariable Long pedidoId) {
		return itemPedidoService.getItensByPedidoId(pedidoId);
	}
	
	@DeleteMapping("/{id}")				//@ApiOperation(value = "Deleta item")
	public ResponseEntity<?> delItemPedido(@PathVariable Long id) {
		itemPedidoService.delItemPedido(id);
		return new ResponseEntity<Void>( HttpStatus.OK );
	}

	
}
