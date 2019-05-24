package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Cliente;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ClientUpdateRequest {

    @NotNull(message = "pag.client.required.id")
    private Long id;

    @NotNull(message = "pag.client.required.name")
    @Size(max = 100, message = "pag.client.max.name")
    private String nome;

    @CPF(message = "pag.client.invalid.cpf")
    @NotNull(message = "pag.client.required.cpf")
    private String cpf;

    @NotNull(message = "pag.client.required.dateofbirth")
    private LocalDate dataNascimento;

    public Cliente toModel() {
        return new Cliente()
            .setId(id)
            .setNome(nome)
            .setCpf(cpf)
            .setDataNascimento(dataNascimento);
    }
}
