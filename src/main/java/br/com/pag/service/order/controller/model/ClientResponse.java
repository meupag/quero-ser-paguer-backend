package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Cliente;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@Data
public class ClientResponse extends ResourceSupport {

    @JsonProperty("id")
    private Long clientId;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public static ClientResponse fromModel(Cliente cliente) {
        return new ClientResponse()
            .setClientId(cliente.getId())
            .setNome(cliente.getNome())
            .setCpf(cliente.getCpf())
            .setDataNascimento(cliente.getDataNascimento());
    }
}
