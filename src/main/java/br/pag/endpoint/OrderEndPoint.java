package br.pag.endpoint;

import br.pag.error.ResponseException;
import br.pag.message.Translator;
import br.pag.model.Order;
import br.pag.repository.CustomerRepository;
import br.pag.repository.OrderRepository;
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
 * Classe de serviços relativos a entidade Pedido.
 *
 * @author brunner.klueger
 */
@RestController
@RequestMapping("pedidos")
@Api(tags = "Order-End-Point")
public class OrderEndPoint {

    private final CustomerRepository customerDAO;
    private final OrderRepository orderDAO;

    @Autowired
    public OrderEndPoint(CustomerRepository customerDAO,
            OrderRepository orderDAO) {
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;
    }

    /**
     * Busca todos os pedidos cadastrados.
     *
     * @return lista de pedidos
     */
    @GetMapping
    @ApiOperation(value = "Busca todos os Pedidos cadastrados no sistema", response = List.class, produces = "application/json")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(orderDAO.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Consulta um pedido por seu ID.
     *
     * @param id id do pedido
     * @return pedido encontrado
     */
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca um Pedido a partir do seu ID", response = Order.class, produces = "application/json")
    public ResponseEntity<?> getOrderById(
            @ApiParam(value = "ID do Pedido que está sendo consultado no banco de dados", required = true) @PathVariable("id") Long id) {
        //verifica a existencia do pedido
        verifyIfOrderExists(id);
        return new ResponseEntity<>(orderDAO.findById(id), HttpStatus.OK);
    }

    /**
     * Deleta um pedido a partir de seu ID.
     *
     * @param id id do pedido
     * @return status de sucesso ou falha ao deletar
     */
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta um Pedido no banco de dados a partir do seu ID", produces = "application/json")
    public ResponseEntity<?> delete(
            @ApiParam(value = "ID do Pedido que está sendo deletado no banco de dados", required = true) @PathVariable("id") Long id) {

        //verifica a existencia do pedido
        verifyIfOrderExists(id);
        orderDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Cadastra um pedido no banco de dados.
     *
     * @param custormerId id do cliente que será associado ao pedido
     * @param order pedido a ser cadastrado
     * @return pedido cadastrado
     */
    @PostMapping(path = "/{customerId}")
    @ApiOperation(value = "Cadastra um Pedido no banco de dados", response = Order.class, produces = "application/json")
    public ResponseEntity<?> save(
            @ApiParam(value = "Id do Cliente que será associado ao pedido", required = true) @PathVariable("customerId") Long custormerId,
            @ApiParam(value = "Objeto Order(Pedido) que será cadastrado no banco de dados", required = true) @Valid @RequestBody Order order) {

        //ID de clinete a ser associado não pode ser nulo
        if (Objects.isNull(custormerId)) {
            throw new ResponseException(Translator.toLocale("Order.Save.IdCustomer.NotNull"));
        }
        //não é possivel cadastrar um pedido que já possui ID
        if (Objects.nonNull(order.getId())) {
            throw new ResponseException(Translator.toLocale("Order.Save.IdOrder.NotNull"));
        }
        return customerDAO.findById(custormerId).map(customer -> {
            order.setCustomer(customer);
            Order savedOrder = orderDAO.save(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.OK);
        }).orElseThrow(() -> new ResponseException(Translator.toLocale("Order.Save.Customer.Id.NotFound")));
    }

    /**
     * Atualiza um pedido.
     *
     * @param order pedido a ser atualizado
     * @return sucesso ou falha
     */
    @PutMapping
    @ApiOperation(value = "Atualiza um Pedido no banco de dados", response = Order.class, produces = "application/json")
    public ResponseEntity<?> update(
            @ApiParam(value = "Objeto Order(Pedido) que será atualizado no banco de dados", required = true) @Valid @RequestBody Order order) {
        Long idOrder = order.getId();
        if (idOrder == null) {//se não possui ID
            throw new ResponseException(Translator.toLocale("Order.Update.Id.IsNull"));
        }
        //verifica a existencia do pedido
        verifyIfOrderExists(idOrder);
        return orderDAO.findById(idOrder)
                .map(updatedOrder -> {
                    updatedOrder.setValue(order.getValue());
                    return new ResponseEntity<>(orderDAO.save(updatedOrder), HttpStatus.OK);
                }).orElseThrow(() -> new ResponseException(Translator.toLocale("Order.Id.NotFound")));
    }

    /**
     * Verifica se um Pedido existe.
     *
     * @param id id do pedido
     */
    private void verifyIfOrderExists(Long id) {
        if (Objects.isNull(id) || Objects.isNull(orderDAO.findById(id).orElse(null))) {
            throw new ResponseException(Translator.toLocale("Order.Id.NotFound", id));
        }
    }
}
