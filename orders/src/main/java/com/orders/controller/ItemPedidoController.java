package com.orders.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.orders.exception.RecordNotFoundException;
import com.orders.model.ItemPedido;
import com.orders.repository.ItemPedidoRepository;

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
@RequestMapping({ "/itemPedidos" })
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "Operação realizada com sucesso."),
    @ApiResponse(code = 401, message = "Você não está autorizado para fazer esta operação."),
    @ApiResponse(code = 403, message = "Acesso proíbido."),
    @ApiResponse(code = 404, message = "Não encontrado.")
})
@Api(value = "ItemPedido", description = "Operações relacionadas ao ItemPedido", tags = "ItemPedido")
public class ItemPedidoController {

    private ItemPedidoRepository repository;

    ItemPedidoController(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Vizualizar uma lista de produtos.", response = ItemPedido.class)
    @GetMapping
    public List<ItemPedido> findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Vizualizar um ItemPedido por ID.", response = ItemPedido.class)
    @GetMapping(path = { "/{id}" })
    @Cacheable(value = "ItemPedido", key = "#id")
    public ResponseEntity<ItemPedido> findById(@ApiParam(value = "Id do ItemPedido à ser recebido", required = true) @PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElseThrow(() -> new RecordNotFoundException("Nenhum ItemPedido encontrado com este id: " + id));
    }

    @ApiOperation(value = "Criar um novo ItemPedido.")
    @PostMapping
    public ItemPedido create(@ApiParam(value = "Novo ItemPedido para ser criado", required = true) @Valid @RequestBody ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }

    @ApiOperation(value = "Atualizar um ItemPedido.")
    @PutMapping(value = "/{id}")
    @CacheEvict(value = "ItemPedido", key = "#id")
    public ResponseEntity<ItemPedido> update(@ApiParam(value = "Id do ItemPedido à ser recebido", required = true) @PathVariable("id") long id, @Valid @RequestBody ItemPedido itemPedido) {
        return repository.findById(id).map(registro -> {
            registro.setIdPedido(itemPedido.getIdPedido());
            registro.setIdProduto(itemPedido.getIdProduto());
            registro.setQuantidade(itemPedido.getQuantidade());
            registro.setPreco(itemPedido.getPreco());
            ItemPedido updated = repository.save(registro);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apagar um ItemPedido.", response = ItemPedido.class)
    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@ApiParam(value = "Id do ItemPedido à ser recebido", required = true) @PathVariable("id") long id) {
        return repository.findById(id).map(registro -> {

            repository.deleteById(id);
            checkCaches();
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new RecordNotFoundException("Nenhum ItemPedido encontrado com este id: " + id));
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
