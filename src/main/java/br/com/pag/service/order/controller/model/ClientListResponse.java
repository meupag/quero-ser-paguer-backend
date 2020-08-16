package br.com.pag.service.order.controller.model;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Getter
public class ClientListResponse extends ResourceSupport {

    private List<ClientResponse> clientResponses;

    public ClientListResponse(List<ClientResponse> clientResponses) {
        this.clientResponses = clientResponses;
    }

    public int getSize() {
        if (clientResponses == null) {
            return 0;
        }
        return clientResponses.size();
    }
}
