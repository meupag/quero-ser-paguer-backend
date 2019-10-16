package com.store.resource;

import com.store.controller.CustomerController;
import com.store.controller.OrderController;
import com.store.domain.Customer;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class CustomerResource extends ResourceSupport {

    private final Customer customer;

    public CustomerResource(Customer customer) {
        this.customer = customer;
        final Integer customerId = customer.getId();
        add(linkTo(CustomerController.class).withRel("customers"));
        add(linkTo(methodOn(OrderController.class).getOrder(customerId, null, null, null, null, null)).withRel("customerOrders"));
        add(linkTo(methodOn(CustomerController.class).getCustomerById(customerId)).withSelfRel());
    }

}