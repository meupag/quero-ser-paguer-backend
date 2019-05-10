package br.com.pag.queroserpaguer.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pag.queroserpaguer.domain.Cliente;
import br.com.pag.queroserpaguer.repository.ClienteRepository;
import br.com.pag.queroserpaguer.service.ClienteService;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    
    @Override
    public Cliente save(Cliente cliente) {
        log.info("Salvando  Cliente : {}", cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        log.info("Obtendo lista de  Clientes");
        return clienteRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        log.info("get Cliente : {}", id);
        return clienteRepository.findById(id);
    }

   
    @Override
    public void delete(Long id) {
        log.info("deletando o Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
}
