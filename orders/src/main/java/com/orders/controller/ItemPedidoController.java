package com.orders.controller;

import java.util.List;

import com.orders.model.ItemPedido;
import com.orders.repository.ItemPedidoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/itensPedidos" })
public class ItemPedidoController {

    private ItemPedidoRepository repository;

    ItemPedidoController(ItemPedidoRepository itemPedidoRepository) {
        this.repository = itemPedidoRepository;
    }

    @GetMapping
    public List<ItemPedido> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<ItemPedido> findById(@PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ItemPedido create(@RequestBody ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemPedido> update(@PathVariable("id") long id, @RequestBody ItemPedido itemPedido) {
        return repository.findById(id).map(registro -> {
            registro.setIdPedido(itemPedido.getIdPedido());
            registro.setIdProduto(itemPedido.getIdProduto());
            registro.setQuantidade(itemPedido.getQuantidade());
            registro.setPreco(itemPedido.getPreco());
            ItemPedido updated = repository.save(registro);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id).map(registro -> {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
