package br.com.pag.service.order.service;

import br.com.pag.service.order.exception.ClientNotFoundByIdException;
import br.com.pag.service.order.model.Cliente;
import br.com.pag.service.order.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = { "client, clients" })
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @CacheEvict(value ="clients", allEntries = true)
    @Transactional
    @NewSpan
    public Cliente create(final Cliente client) {
        return repository.save(client);
    }

    @Cacheable(value ="client", key = "#clientId", unless="#result == null")
    @NewSpan
    public Optional<Cliente> findById(@SpanTag("clientId") final Long clientId) {
        return repository.findById(clientId);
    }

    @Cacheable("clients")
    @NewSpan
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Transactional
    @CachePut(value="client", key = "#client.id")
    @CacheEvict(value ="clients", allEntries = true)
    @NewSpan
    public Cliente update(final Cliente client) {
        return repository.findById(client.getId())
            .map(c -> {
                c.setNome(client.getNome())
                    .setCpf(client.getCpf())
                    .setDataNascimento(client.getDataNascimento());
                return repository.save(c);
            })
            .orElseThrow(() -> new ClientNotFoundByIdException(client.getId()));
    }
}
