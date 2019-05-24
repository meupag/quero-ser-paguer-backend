package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.OrderCreateRequest;
import br.com.pag.service.order.controller.model.OrderItemResponse;
import br.com.pag.service.order.controller.model.OrderResponse;
import br.com.pag.service.order.controller.model.OrderUpdateRequest;
import br.com.pag.service.order.exception.OrderNotFoundByIdException;
import br.com.pag.service.order.exception.OrdersNotFoundException;
import br.com.pag.service.order.model.ItemPedido;
import br.com.pag.service.order.model.Pedido;
import br.com.pag.service.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController implements IOrderController {

    @Autowired
    private OrderService orderService;

    @Override
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid final OrderCreateRequest request) {
        log.info("Creating a new order");

        Pedido order = request.toModel();
        final Pedido createdOrder = orderService.create(order);

        log.info("Successfuly created order no. [{}]", createdOrder.getId());

        return ResponseEntity.ok(OrderResponse.fromModel(createdOrder));
    }

    @Override
    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<OrderResponse> update(@RequestBody @Valid final OrderUpdateRequest request) {
        log.info("Updating order with id [{}]", request.getId());

        Pedido order = request.toModel();
        final Pedido updatedOrder = orderService.update(order);

        log.info("Successfuly updated order no. [{}]", updatedOrder.getId());

        return ResponseEntity.ok(OrderResponse.fromModel(updatedOrder));
    }

    @Override
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> delete(@PathVariable final Long orderId) {
        log.info("Removing order with id [{}]", orderId);

        final Pedido deletedOrder = orderService.delete(orderId);

        log.info("Successfuly deleted order no. [{}]", deletedOrder.getId());

        return ResponseEntity.ok(OrderResponse.fromModel(deletedOrder));
    }

    @Override
    @DeleteMapping("/{orderId}/orderItems/{orderItemId}")
    public ResponseEntity<OrderItemResponse> deleteItem(@PathVariable final Long orderId, @PathVariable final Long orderItemId) {
        log.info("Removing order item no. [{}] for order [{}]", orderItemId, orderId);

        final ItemPedido deletedOrderItem = orderService.deleteOrderItem(orderId, orderItemId);

        log.info("Successfuly deleted order item no. [{}] for order [{}]", deletedOrderItem.getId(), orderId);

        return ResponseEntity.ok(OrderItemResponse.fromModel(deletedOrderItem));
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderResponse>> findAll() {
        log.info("Searching all orders");

        final List<Pedido> orders = orderService.findAll();

        if (orders.isEmpty()) {
            throw new OrdersNotFoundException();
        }

        final List<OrderResponse> ordersResponse = orders.stream()
            .map(OrderResponse::fromModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(ordersResponse);
    }

    @Override
    @GetMapping(value = "/{orderId}", produces = "application/json")
    public ResponseEntity<OrderResponse> findById(@PathVariable final Long orderId) {
        log.info("Searching order [{}]", orderId);

        return orderService.findById(orderId)
            .map(o -> {
                return ResponseEntity.ok(OrderResponse.fromModel(o));
            })
            .orElseThrow(() -> new OrderNotFoundByIdException(orderId));
    }
}
