package com.orders.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.orders.exception.RecordNotFoundException;
import com.orders.model.Cliente;
import com.orders.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping({ "/clientes" })
public class ClienteController {

    private ClienteRepository repository;

    ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = { "/{id}" })
    @Cacheable(value = "cliente", key = "#id")
    public ResponseEntity<Cliente> findById(@PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElseThrow(() -> new RecordNotFoundException("Nenhum cliente encontrado com este id: " + id));
    }

    @GetMapping(path = { "/{nome}/{cpf}" })
    @Cacheable(value = "cliente", key = "#nome")
    public ResponseEntity<Cliente> clienteLogin(@PathVariable String nome, @PathVariable String cpf) {
        return repository.findByNomeAndCpf(nome, cpf).map(registro -> ResponseEntity.ok().body(registro))
                .orElseThrow(() -> new RecordNotFoundException("Cliente não encontrado!"));
    }

    @PostMapping
    public Cliente create(@Valid @RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @PutMapping(value = "/{id}")
    @CacheEvict(value = "cliente", key = "#id")
    public ResponseEntity<Cliente> update(@PathVariable("id") long id, @Valid @RequestBody Cliente cliente) {
        return repository.findById(id).map(registro -> {
            registro.setNome(cliente.getNome());
            registro.setCpf(cliente.getCpf());
            registro.setDataNascimento(cliente.getDataNascimento());

            Cliente updated = repository.save(registro);

            return ResponseEntity.ok().body(updated);

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id).map(registro -> {

            repository.deleteById(id);
            checkCaches();
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new RecordNotFoundException("Nenhum cliente encontrado com este id: " + id));
    }

    @Autowired
    CacheManager cacheManager;

    public void evictSingleCacheValue(String cacheName, String cacheKey) {
        cacheManager.getCache(cacheName).evict(cacheKey);
    }

    public void evictAllCacheValues(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }

    public Collection<String> checkCaches() {
        Collection<String> a = cacheManager.getCacheNames();
        return a;
    }

}
