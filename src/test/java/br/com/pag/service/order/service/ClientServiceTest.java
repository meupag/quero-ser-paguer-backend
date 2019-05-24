package br.com.pag.service.order.service;

import br.com.pag.service.order.exception.ClientNotFoundByIdException;
import br.com.pag.service.order.model.Cliente;
import br.com.pag.service.order.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void mustUpdateClient() {
        when(repository.findById(Client1.CLIENT_ID))
            .thenReturn(Optional.of(new Cliente()
                .setId(Client1.CLIENT_ID)
                .setNome(Client1.CLIENT_NAME)
                .setCpf(Client1.CPF)
                .setDataNascimento(Client1.DATE_OF_BIRTH)));

        when(repository.save(Mockito.any(Cliente.class)))
            .thenAnswer(i -> (Cliente) i.getArguments()[0]);

        Cliente savedClient = clientService.update(createClientData());

        assertThat(savedClient.getId()).isEqualTo(Client2.CLIENT_ID);
        assertThat(savedClient.getNome()).isEqualTo(Client2.CLIENT_NAME);
        assertThat(savedClient.getCpf()).isEqualTo(Client2.CPF);
        assertThat(savedClient.getDataNascimento()).isEqualTo(Client2.DATE_OF_BIRTH);
    }

    @Test
    void mustThrowException_whenClientNotFound() {
        when(repository.findById(Client1.CLIENT_ID))
            .thenReturn(Optional.empty());

        ClientNotFoundByIdException exception = assertThrows(
            ClientNotFoundByIdException.class, () -> clientService.update(createClientData()));

        Assertions.assertThat(exception.getMessage()).isEqualTo("pag.client.byid.notfound");
    }

    private Cliente createClientData() {
        return new Cliente()
            .setId(Client2.CLIENT_ID)
            .setNome(Client2.CLIENT_NAME)
            .setCpf(Client2.CPF)
            .setDataNascimento(Client2.DATE_OF_BIRTH);
    }

    private Cliente createClientSavedData() {
        return new Cliente()
            .setId(Client2.CLIENT_ID)
            .setNome(Client2.CLIENT_NAME)
            .setCpf(Client2.CPF)
            .setDataNascimento(Client2.DATE_OF_BIRTH);
    }

    interface Client1 {
        Long CLIENT_ID = 1000L;
        String CLIENT_NAME = "CLIENT_1000";
        String CPF = "00000000000";
        LocalDate DATE_OF_BIRTH = LocalDate.of(2019, 5, 1);
    }

    interface Client2 {
        Long CLIENT_ID = 1000L;
        String CLIENT_NAME = "CLIENT_2000";
        String CPF = "11111111111";
        LocalDate DATE_OF_BIRTH = LocalDate.of(2018, 4, 30);
    }
}
