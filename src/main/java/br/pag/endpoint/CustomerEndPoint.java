package br.pag.endpoint;

import br.pag.error.ResponseException;
import br.pag.message.Translator;
import br.pag.model.Customer;
import br.pag.repository.CustomerRepository;
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
    public ResponseEntity<?> listAll(Pageable pageable) {
        String test = Translator.toLocale("error.notFound", 1);
        System.out.println(test);
        return new ResponseEntity<>(customerDAO.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Consulta um cliente por seu ID.
     *
     * @param id id do cliente
     * @return cliente encontrado
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") long id) {
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
    public ResponseEntity<?> delete(@PathVariable("id") long id) {

        //verifica a existencia do cliente
        verifyIfCustomerExists(id);
        customerDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Cadastra um cliente no banco de dados.
     *
     * @param newCustomer cliente a ser cadastrado
     * @return cliente cadastrado
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Customer newCustomer) {
        //verifica se o objeto é nulo
        if (Objects.isNull(newCustomer)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> update(@Valid @RequestBody Customer updatedCustomer) {
        //verifica se o objeto é nulo
        if (Objects.isNull(updatedCustomer)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long idCustomer = updatedCustomer.getId();
        if (idCustomer != null) {//se possui ID
            //verifica a existencia do cliente
            verifyIfCustomerExists(idCustomer);
            customerDAO.save(updatedCustomer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private void verifyIfCustomerExists(Long id) {
        if (!customerDAO.findById(id).isPresent()) {
            throw new ResponseException(Translator.toLocale("error.notFound", id));
        }
    }
}
