package main.java.controllers;

import main.java.models.ItemPedido;
import main.java.repository.ItemPedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class ItemPedidoController {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @GetMapping("/item_pedidos")
    public @ResponseBody Iterable<ItemPedido> getAllItemPedidos() {
        return itemPedidoRepository.findAll();
    }
}