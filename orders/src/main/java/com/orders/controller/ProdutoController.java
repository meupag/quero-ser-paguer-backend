package com.orders.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.orders.exception.RecordNotFoundException;
import com.orders.model.Produto;
import com.orders.repository.ProdutoRepository;

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
@RequestMapping({ "/produtos" })
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "Operação realizada com sucesso."),
    @ApiResponse(code = 401, message = "Você não está autorizado para fazer esta operação."),
    @ApiResponse(code = 403, message = "Acesso proíbido."),
    @ApiResponse(code = 404, message = "Não encontrado.")
})
@Api(value = "Produto", description = "Operações relacionadas ao Produto", tags = "Produto")
public class ProdutoController {

    @Autowired
    CacheManager cacheManager;
    
    private ProdutoRepository repository;

    ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Vizualizar uma lista de produtos.", response = Produto.class)
    @GetMapping
    public List<Produto> findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Vizualizar um Produto por ID.", response = Produto.class)
    @GetMapping(path = { "/{id}" })
    @Cacheable(value = "Produto", key = "#id")
    public ResponseEntity<Produto> findById(@ApiParam(value = "Id do Produto à ser recebido", required = true) @PathVariable long id) {
        return repository.findById(id).map(registro -> ResponseEntity.ok().body(registro))
                .orElseThrow(() -> new RecordNotFoundException("Nenhum Produto encontrado com este id: " + id));
    }

    @ApiOperation(value = "Criar um novo Produto.")
    @PostMapping
    public Produto create(@ApiParam(value = "Novo Produto para ser criado", required = true) @Valid @RequestBody Produto produto) {
        return repository.save(produto);
    }

    @ApiOperation(value = "Atualizar um Produto.")
    @PutMapping(value = "/{id}")
    @CacheEvict(value = "Produto", key = "#id")
    public ResponseEntity<Produto> update(@ApiParam(value = "Id do Produto à ser recebido", required = true) @PathVariable("id") long id, @Valid @RequestBody Produto produto) {
        return repository.findById(id).map(registro -> {
            registro.setNome(produto.getNome());
            registro.setPrecoSugerido(produto.getPrecoSugerido());
            Produto updated = repository.save(registro);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Apagar um Produto.", response = Produto.class)
    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<?> delete(@ApiParam(value = "Id do Produto à ser recebido", required = true) @PathVariable("id") long id) {
        return repository.findById(id).map(registro -> {

            repository.deleteById(id);
            checkCaches();
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new RecordNotFoundException("Nenhum Produto encontrado com este id: " + id));
    }

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
