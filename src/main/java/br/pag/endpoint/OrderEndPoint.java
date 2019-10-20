package br.pag.endpoint;

import br.pag.model.Order;
import br.pag.repository.OrderRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderEndPoint {

    private final OrderRepository orderDAO;

    @Autowired
    public OrderEndPoint(OrderRepository orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(orderDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {

        if (Objects.isNull(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Order> orderFound = orderDAO.findById(id);
        if (orderFound.isPresent()) {
            return new ResponseEntity<>(orderFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Order> orderFound = orderDAO.findById(id);
        if (orderFound.isPresent()) {
            orderDAO.delete(orderFound.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Order newOrder) {
        if (Objects.isNull(newOrder)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newOrder = orderDAO.save(newOrder);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Order order) {
        if (Objects.isNull(order)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderDAO.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
