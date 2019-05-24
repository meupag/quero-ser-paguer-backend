package br.com.pag.service.order.service;

import br.com.pag.service.order.controller.model.ClientResponse;
import br.com.pag.service.order.exception.ClientNotFoundByIdException;
import br.com.pag.service.order.model.Cliente;
import br.com.pag.service.order.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional
    public Cliente create(final Cliente client) {
        return repository.save(client);
    }

    public Optional<Cliente> findById(final Long clientId) {
        return repository.findById(clientId);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Transactional
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
