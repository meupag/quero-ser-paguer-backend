package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Cliente;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponse {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public static ClientResponse fromModel(Cliente cliente) {
        return new ClientResponse()
            .setId(cliente.getId())
            .setNome(cliente.getNome())
            .setCpf(cliente.getCpf())
            .setDataNascimento(cliente.getDataNascimento());
    }
}
