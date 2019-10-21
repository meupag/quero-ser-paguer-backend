package br.pag.endpoint;

import br.pag.error.ResponseException;
import br.pag.message.Translator;
import br.pag.model.Customer;
import br.pag.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de serviços relativos a entidade Cliente.
 *
 * @author brunner.klueger
 */
@RestController
@RequestMapping("clientes")
@Api(tags = "Customer-End-Point")
public class CustomerEndPoint {

    private final CustomerRepository customerDAO;

    @Autowired
    public CustomerEndPoint(CustomerRepository customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * Busca todos os clientes cadastrados.
     *
     * @return lista de clientes
     */
    @GetMapping
    @ApiOperation(value = "Busca todos os Clientes cadastrados no sistema", response = List.class, produces = "application/json")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(customerDAO.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Consulta um cliente por seu ID.
     *
     * @param id id do cliente
     * @return cliente encontrado
     */
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca um cliente a partir do seu ID", response = Customer.class, produces = "application/json")
    public ResponseEntity<?> getCustomerById(
            @ApiParam(value = "ID do Cliente que está sendo consultado no banco de dados", required = true) @PathVariable("id") Long id) {
        //verifica a existencia do cliente
        verifyIfCustomerExists(id);
        return new ResponseEntity<>(customerDAO.findById(id), HttpStatus.OK);
    }

    /**
     * Deleta um cliente a partir de seu ID.
     *
     * @param id id do cliente
     * @return status de sucesso ou falha ao deletar
     */
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta um Cliente no banco de dados a partir do seu ID", produces = "application/json")
    public ResponseEntity<?> delete(
            @ApiParam(value = "ID do Cliente que está sendo deletado no banco de dados", required = true) @PathVariable("id") Long id) {

        //verifica a existencia do cliente
        verifyIfCustomerExists(id);
        customerDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Cadastra um cliente no banco de dados.
     *
     * @param newCustomer cliente a ser cadastrado
     * @return cliente cadastrado
     */
    @PostMapping
    @ApiOperation(value = "Cadastra um Cliente no banco de dados", response = Customer.class, produces = "application/json")
    public ResponseEntity<?> save(
            @ApiParam(value = "Objeto Customer(Cliente) que será cadastrado no banco de dados", required = true) @Valid @RequestBody Customer newCustomer) {
        //não é possivel cadastrar um cliente que já possui ID
        if (newCustomer.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newCustomer = customerDAO.save(newCustomer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    /**
     * Atualiza um cliente.
     *
     * @param updatedCustomer cliente a ser atualizado
     * @return sucesso ou falha
     */
    @PutMapping
    @ApiOperation(value = "Atualiza um Cliente no banco de dados", response = Customer.class, produces = "application/json")
    public ResponseEntity<?> update(
            @ApiParam(value = "Objeto Customer(Cliente) que será atualizado no banco de dados", required = true) @Valid @RequestBody Customer updatedCustomer) {
        Long idCustomer = updatedCustomer.getId();
        if (idCustomer != null) {//se possui ID
            //verifica a existencia do cliente
            verifyIfCustomerExists(idCustomer);
            updatedCustomer = customerDAO.save(updatedCustomer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Verifica se um Cliente existe.
     *
     * @param id id do cliente
     */
    private void verifyIfCustomerExists(Long id) {
        if (Objects.isNull(id) || Objects.isNull(customerDAO.findById(id).orElse(null))) {
            throw new ResponseException(Translator.toLocale("error.notFound", id));
        }
    }
}
