package com.order.service.customer.event.impl;

import com.order.service.customer.event.CustomerEventChain;
import com.order.service.customer.receiver.request.CustomerEventRequest;
import com.order.service.customer.receiver.request.CustomerEventType;
import com.order.service.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerEventSave implements CustomerEventChain {

    private final CustomerRepository customerRepository;

    public CustomerEventSave(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void execute(CustomerEventRequest customerEventRequest) {
        log.info("Executing save event");

        this.customerRepository.save(customerEventRequest.getCustomer());
    }

    @Override
    public boolean canHandle(CustomerEventRequest customerEventRequest) {
        return CustomerEventType.SAVE.equals(customerEventRequest.getCustomerEventType());
    }
}
