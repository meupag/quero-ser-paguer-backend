package com.order.service.customer.service.impl;

import com.order.service.customer.exception.CustomerPreConditionException;
import com.order.service.customer.service.CustomerService;
import com.order.service.customer.document.Customer;
import com.order.service.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void valid(String id) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);
        if(!customerOptional.isPresent()){
            throw new CustomerPreConditionException(id);
        }
    }
}