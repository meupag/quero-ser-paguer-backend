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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping({ "/clientes" })
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "Operação realizada com sucesso."),
    @ApiResponse(code = 401, message = "Você não está autorizado para fazer esta operação."),
    @ApiResponse(code = 403, message = "Acesso proíbido."),
    @ApiResponse(code = 404, message = "Não encontrado.")
})
@Api(value = "cliente", description = "Operações relacionadas ao Cliente", tags = "CLIENTE")
public class ClienteController {

    private ClienteRepository repository;

    ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Vizualizar uma lista de clientes.", response = Cliente.class)
    @GetMapping
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Vizualizar um cliente por ID.", response = Cliente.class)
    @GetMapping(path = { "/{id}" })
    @Cacheable(value = "cliente", key = "#id")
    public ResponseEntity<Cliente> findById(@ApiParam(value = "Id do cliente à ser recebido", required = true) @PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElseThrow(() -> new RecordNotFoundException("Nenhum cliente encontrado com este id: " + id));
    }

    @ApiOperation(value = "Criar um novo cliente.")
    @PostMapping
    public Cliente create(@ApiParam(value = "Novo cliente para ser criado", required = true) @Valid @RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @ApiOperation(value = "Atualizar um cliente.")
    @PutMapping(value = "/{id}")
    @CacheEvict(value = "cliente", key = "#id")
    public ResponseEntity<Cliente> update(@ApiParam(value = "Id do cliente à ser recebido", required = true) @PathVariable("id") long id, @Valid @RequestBody Cliente cliente) {
        return repository.findById(id).map(registro -> {
            registro.setNome(cliente.getNome());
            registro.setCpf(cliente.getCpf());
            registro.setDataNascimento(cliente.getDataNascimento());

            Cliente updated = repository.save(registro);

            return ResponseEntity.ok().body(updated);

        }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apagar um cliente.", response = Cliente.class)
    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@ApiParam(value = "Id do cliente à ser recebido", required = true) @PathVariable("id") long id) {
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
