package com.orders.controller;

import java.util.List;

import com.orders.model.Pedido;
import com.orders.repository.PedidoRepository;

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
@RequestMapping({ "/pedidos" })
public class PedidoController {

    private PedidoRepository repository;

    PedidoController(PedidoRepository pedidoRepository) {
        this.repository = pedidoRepository;
    }

    @GetMapping
    public List<Pedido> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<Pedido> findById(@PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pedido create(@RequestBody Pedido pedido) {
        return repository.save(pedido);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pedido> update(@PathVariable("id") long id, @RequestBody Pedido pedido) {
        return repository.findById(id).map(registro -> {
            registro.setIdCliente(pedido.getIdCliente());
            registro.setValor(pedido.getValor());
            Pedido updated = repository.save(registro);
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
