package com.order.service.customer.event.impl;

import com.order.service.customer.document.Customer;
import com.order.service.customer.event.CustomerEventChain;
import com.order.service.customer.exception.CustomerEventNotFoundException;
import com.order.service.customer.receiver.request.CustomerEventRequest;
import com.order.service.customer.receiver.request.CustomerEventType;
import com.order.service.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CustomerEventDelete implements CustomerEventChain {

    private final CustomerRepository customerRepository;

    public CustomerEventDelete(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void execute(CustomerEventRequest customerEventRequest) {
        log.info("Executing delete event");

        Optional<Customer> customerOptional = this.customerRepository.findById(customerEventRequest.getCustomer().getId());
        if(!customerOptional.isPresent()){
            throw new CustomerEventNotFoundException(customerEventRequest.getCustomer().getId());
        }
        this.customerRepository.delete(customerEventRequest.getCustomer());
    }

    @Override
    public boolean canHandle(CustomerEventRequest customerEventRequest) {
        return CustomerEventType.DELETE.equals(customerEventRequest.getCustomerEventType());
    }
}
