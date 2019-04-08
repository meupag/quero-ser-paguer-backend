package com.orders.controller;

import java.util.List;

import com.orders.model.Produto;
import com.orders.repository.ProdutoRepository;

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
@RequestMapping({ "/produtos" })
public class ProdutoController {

    private ProdutoRepository repository;

    ProdutoController(ProdutoRepository produtoRepository) {
        this.repository = produtoRepository;
    }

    @GetMapping
    public List<Produto> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<Produto> findById(@PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produto create(@RequestBody Produto itemPedido) {
        return repository.save(itemPedido);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Produto> update(@PathVariable("id") long id, @RequestBody Produto itemPedido) {
        return repository.findById(id).map(registro -> {
            registro.setNome(itemPedido.getNome());
            registro.setPrecoSugerido(itemPedido.getPrecoSugerido());
            Produto updated = repository.save(registro);
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
