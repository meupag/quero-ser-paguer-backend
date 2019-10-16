package com.store.resource;

import com.store.controller.OrderController;
import com.store.controller.OrderItemController;
import com.store.domain.Order;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class OrderResource extends ResourceSupport {

    private final Order order;

    public OrderResource(Order order) {
        this.order = order;
        final Integer customerId = order.getCustomer().getId();
        final Integer orderId = order.getId();

        add(linkTo(methodOn(OrderItemController.class).getOrderItem(customerId, orderId, null, null, null, null, null)).withRel("customerOrderItems"));
        add(linkTo(methodOn(OrderController.class).getOrderById(customerId, orderId)).withSelfRel());
    }

}