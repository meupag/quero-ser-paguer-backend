package com.orders.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.orders.exception.RecordNotFoundException;
import com.orders.model.Pedido;
import com.orders.repository.PedidoRepository;

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
@RequestMapping({ "/pedidos" })
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "Operação realizada com sucesso."),
    @ApiResponse(code = 401, message = "Você não está autorizado para fazer esta operação."),
    @ApiResponse(code = 403, message = "Acesso proíbido."),
    @ApiResponse(code = 404, message = "Não encontrado.")
})
@Api(value = "Pedido", description = "Operações relacionadas ao Pedido", tags = "Pedido")
public class PedidoController {

    private PedidoRepository repository;

    PedidoController(PedidoRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Vizualizar uma lista de pedidos.", response = Pedido.class)
    @GetMapping
    public List<Pedido> findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Vizualizar um Pedido por ID.", response = Pedido.class)
    @GetMapping(path = { "/{id}" })
    @Cacheable(value = "Pedido", key = "#id")
    public ResponseEntity<Pedido> findById(@ApiParam(value = "Id do Pedido à ser recebido", required = true) @PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElseThrow(() -> new RecordNotFoundException("Nenhum Pedido encontrado com este id: " + id));
    }

    @ApiOperation(value = "Criar um novo Pedido.")
    @PostMapping
    public Pedido create(@ApiParam(value = "Novo Pedido para ser criado", required = true) @Valid @RequestBody Pedido pedido) {
        return repository.save(pedido);
    }

    @ApiOperation(value = "Atualizar um Pedido.")
    @PutMapping(value = "/{id}")
    @CacheEvict(value = "Pedido", key = "#id")
    public ResponseEntity<Pedido> update(@ApiParam(value = "Id do Pedido à ser recebido", required = true) @PathVariable("id") long id, @Valid @RequestBody Pedido pedido) {
        return repository.findById(id).map(registro -> {
            registro.setIdCliente(pedido.getIdCliente());
            registro.setValor(pedido.getValor());
            Pedido updated = repository.save(registro);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apagar um Pedido.", response = Pedido.class)
    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@ApiParam(value = "Id do Pedido à ser recebido", required = true) @PathVariable("id") long id) {
        return repository.findById(id).map(registro -> {

            repository.deleteById(id);
            checkCaches();
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new RecordNotFoundException("Nenhum Pedido encontrado com este id: " + id));
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
