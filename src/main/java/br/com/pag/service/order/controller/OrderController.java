package br.com.pag.service.order.controller;

import br.com.pag.service.order.controller.model.OrderCreateRequest;
import br.com.pag.service.order.controller.model.OrderItemResponse;
import br.com.pag.service.order.controller.model.OrderResponse;
import br.com.pag.service.order.controller.model.OrderUpdateRequest;
import br.com.pag.service.order.exception.OrderNotFoundByIdException;
import br.com.pag.service.order.exception.OrdersNotFoundException;
import br.com.pag.service.order.model.ItemPedido;
import br.com.pag.service.order.model.Pair;
import br.com.pag.service.order.model.Pedido;
import br.com.pag.service.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController implements IOrderController {

    @Autowired
    private OrderService orderService;

    @Override
    @PreAuthorize("hasRole('order_write')")
    @PostMapping(produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid final OrderCreateRequest request) {
        log.info("Creating a new order");

        Pedido order = request.toModel();
        final Pedido createdOrder = orderService.create(order);
        final OrderResponse orderResponse = OrderResponse.fromModel(createdOrder);

        addLinks(createdOrder, orderResponse);
        addLinks(createdOrder, orderResponse.getItens());

        log.info("Successfuly created order no. [{}]", createdOrder.getId());

        return ResponseEntity.ok(orderResponse);
    }

    @Override
    @PreAuthorize("hasRole('order_write')")
    @PutMapping(produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<OrderResponse> update(@RequestBody @Valid final OrderUpdateRequest request) {
        log.info("Updating order with id [{}]", request.getId());

        Pedido order = request.toModel();
        final Pedido updatedOrder = orderService.update(order);
        final OrderResponse orderResponse = OrderResponse.fromModel(updatedOrder);

        addLinks(updatedOrder, orderResponse);
        addLinks(updatedOrder, orderResponse.getItens());

        log.info("Successfuly updated order no. [{}]", updatedOrder.getId());

        return ResponseEntity.ok(orderResponse);
    }

    @Override
    @PreAuthorize("hasRole('order_delete')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> delete(@PathVariable final Long orderId) {
        log.info("Removing order with id [{}]", orderId);

        final Pedido deletedOrder = orderService.delete(orderId);

        log.info("Successfuly deleted order no. [{}]", deletedOrder.getId());

        return ResponseEntity.ok(OrderResponse.fromModel(deletedOrder));
    }

    @Override
    @PreAuthorize("hasRole('order_delete')")
    @DeleteMapping("/{orderId}/orderItems/{orderItemId}")
    public ResponseEntity<OrderItemResponse> deleteItem(@PathVariable final Long orderId, @PathVariable final Long orderItemId) {
        log.info("Removing order item no. [{}] for order [{}]", orderItemId, orderId);

        final ItemPedido deletedOrderItem = orderService.deleteOrderItem(orderId, orderItemId);

        log.info("Successfuly deleted order item no. [{}] for order [{}]", deletedOrderItem.getId(), orderId);

        return ResponseEntity.ok(OrderItemResponse.fromModel(deletedOrderItem));
    }

    @Override
    @PreAuthorize("hasRole('order_read') or hasRole('order_write') or hasRole('order_delete')")
    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<List<OrderResponse>> findAll() {
        log.info("Searching all orders");

        final List<Pedido> orders = orderService.findAll();

        if (orders.isEmpty()) {
            throw new OrdersNotFoundException();
        }

        final List<OrderResponse> ordersResponse = orders.stream()
            .map(o -> {
                return new Pair<>(o, OrderResponse.fromModel(o));
            })
            .map(pair -> {
                addSelfLink(pair.getValue());
                addLinks(pair.getKey(), pair.getValue().getItens());
                return pair.getValue();
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(ordersResponse);
    }

    @Override
    @PreAuthorize("hasRole('order_read') or hasRole('order_write') or hasRole('order_delete')")
    @GetMapping(value = "/{orderId}", produces = "application/hal+json")
    public ResponseEntity<OrderResponse> findById(@PathVariable final Long orderId) {
        log.info("Searching order [{}]", orderId);

        return orderService.findById(orderId)
            .map(o -> {
                final OrderResponse or = OrderResponse.fromModel(o);
                addSelfLink(or);
                addLinks(o, or.getItens());
                return ResponseEntity.ok(or);
            })
            .orElseThrow(() -> new OrderNotFoundByIdException(orderId));
    }

    private void addLinks(final Pedido order, final OrderResponse orderResponse) {
        Link allLink = linkTo(methodOn(OrderController.class).findAll()).withRel("allOrders");
        Link selfLink = linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel();

        orderResponse.add(allLink);
        orderResponse.add(selfLink);
    }

    private void addLinks(final Pedido order, final List<OrderItemResponse> orderItemsResponse) {
        orderItemsResponse
            .forEach(oir -> {
                final Link selfLink = linkTo(
                    methodOn(OrderController.class).deleteItem(order.getId(), oir.getOrderItemId())).withRel("delete");
                oir.add(selfLink);
            });
    }

    private OrderResponse addSelfLink(OrderResponse orderResponse) {
        Link selfLink = linkTo(methodOn(OrderController.class).findById(orderResponse.getIdOrder())).withSelfRel();
        orderResponse.add(selfLink);
        return orderResponse;
    }
}
