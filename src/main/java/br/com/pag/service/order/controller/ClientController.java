package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.ClientCreateRequest;
import br.com.pag.service.order.controller.model.ClientListResponse;
import br.com.pag.service.order.controller.model.ClientResponse;
import br.com.pag.service.order.controller.model.ClientUpdateRequest;
import br.com.pag.service.order.exception.ClientNotFoundByIdException;
import br.com.pag.service.order.exception.ClientsNotFoundException;
import br.com.pag.service.order.model.Cliente;
import br.com.pag.service.order.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/clients")
public class ClientController implements IClientController {

    @Autowired
    private ClientService clientService;

    @Override
    @PreAuthorize("hasRole('client_write')")
    @PostMapping(produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid final ClientCreateRequest request) {
        log.info("Creating a new Client");

        Cliente client = request.toModel();
        final Cliente clientCreated = clientService.create(client);
        final ClientResponse clientResponse = ClientResponse.fromModel(clientCreated);

        addLinks(clientCreated, clientResponse);

        return ResponseEntity.ok(clientResponse);
    }

    @Override
    @PreAuthorize("hasRole('client_write')")
    @PutMapping(produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<ClientResponse> update(@RequestBody @Valid ClientUpdateRequest request) {
        log.info("Updating Client [{}]", request.getId());

        Cliente client = request.toModel();
        final Cliente clientUpdated = clientService.update(client);
        final ClientResponse clientResponse = ClientResponse.fromModel(clientUpdated);

        addLinks(clientUpdated, clientResponse);

        return ResponseEntity.ok(clientResponse);
    }

    @Override
    @PreAuthorize("hasRole('client_read') or hasRole('client_write')")
    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<ClientListResponse> findAll() {
        log.info("Returning all clients");

        final List<Cliente> clients = clientService.findAll();

        if (clients.isEmpty()) {
            log.warn("There are no clients to return");
            throw new ClientsNotFoundException();
        }

        log.info("Returning {} clients", clients.size());

        final List<ClientResponse> clientsResponse = clients.stream()
            .map(ClientResponse::fromModel)
            .map(this::addSelfLink)
            .collect(Collectors.toList());

        final ClientListResponse listResponse = new ClientListResponse(clientsResponse);
        addAllLink(listResponse);

        return ResponseEntity.ok(listResponse);
    }

    @Override
    @PreAuthorize("hasRole('client_read') or hasRole('client_write')")
    @GetMapping(value = "/{clientId}", produces = "application/hal+json")
    public ResponseEntity<ClientResponse> findById(@PathVariable final Long clientId) {
        log.info("Returning client with id [{}]", clientId);

        return clientService.findById(clientId)
            .map(c -> {
                final ClientResponse clientResponse = ClientResponse.fromModel(c);
                addAllLink(clientResponse);
                return ResponseEntity.ok(clientResponse);
            })
            .orElseThrow(() -> new ClientNotFoundByIdException(clientId));
    }

    private void addLinks(Cliente client, ClientResponse clientResponse) {
        Link allLink = linkTo(methodOn(ClientController.class).findAll()).withRel("allClients");
        Link selfLink = linkTo(methodOn(ClientController.class).findById(client.getId())).withSelfRel();

        clientResponse.add(allLink);
        clientResponse.add(selfLink);
    }

    private ClientResponse addSelfLink(ClientResponse clientResponse) {
        Link selfLink = linkTo(methodOn(ClientController.class).findById(clientResponse.getClientId())).withSelfRel();
        clientResponse.add(selfLink);
        return clientResponse;
    }

    private void addAllLink(ClientResponse clientResponse) {
        Link allLink = linkTo(methodOn(ClientController.class).findAll()).withRel("allClients");
        clientResponse.add(allLink);
    }

    private void addAllLink(ClientListResponse listResponse) {
        Link allLink = linkTo(methodOn(ClientController.class).findAll()).withRel("allClients");
        listResponse.add(allLink);
    }
}
