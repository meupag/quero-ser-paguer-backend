package com.customer.service.customer.service.impl;

import com.customer.service.customer.document.Customer;
import com.customer.service.customer.exception.CustomerAlreadyExistException;
import com.customer.service.customer.exception.CustomerNotFoundException;
import com.customer.service.customer.exception.CustomerPreConditionException;
import com.customer.service.customer.repository.CustomerRepository;
import com.customer.service.customer.service.CustomerService;
import com.customer.service.customer.validator.DocumentValidator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CustomerServiceImpl implements CustomerService {

    private final DocumentValidator documentValidator;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(DocumentValidator documentValidator, CustomerRepository customerRepository) {
        this.documentValidator = documentValidator;
        this.customerRepository = customerRepository;
    }

    @Override
    @CacheEvict(value = "list-customer", allEntries = true)
    public Customer save(Customer customer) {
        this.documentValidator.validate(customer.getDocument());

        Optional<Customer> customerDocumentOptional = this.customerRepository.findByDocument(customer.getDocument());
        if (customerDocumentOptional.isPresent()) {
            throw new CustomerAlreadyExistException(customer.getDocument());
        }
        return this.customerRepository.save(customer);
    }

    @Override
    @Cacheable(value = "list-customer")
    public List<Customer> findAll() {
        List<Customer> customers = this.customerRepository.findAll();
        if(customers.isEmpty()){
            throw new CustomerNotFoundException();
        }
        return customers;
    }

    @Override
    @Cacheable(value = "list-customer")
    public Customer findById(String id) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);
        return customerOptional.orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    @CacheEvict(value = "list-customer", allEntries = true)
    public Customer updateById(String id, Customer customer) {
        this.documentValidator.validate(customer.getDocument());
        Optional<Customer> customerOptional = this.customerRepository.findById(id);
        if(!customerOptional.isPresent()){
            throw new CustomerPreConditionException();
        }
        Optional<Customer> customerDocumentOptional = this.customerRepository.findByDocument(customer.getDocument());
        customerDocumentOptional.ifPresent(c -> {
            if(!c.getId().equals(id) && c.getDocument().equals(customer.getDocument())){
                throw new CustomerAlreadyExistException(customer.getDocument());
            }
        });
        customer.setId(id);
        return this.customerRepository.save(customer);
    }

    @Override
    @CacheEvict(value = "list-customer", allEntries = true)
    public void deleteById(String id) {
        Optional<Customer> customerOptional = this.customerRepository.findById(id);
        if(!customerOptional.isPresent()){
            throw new CustomerPreConditionException();
        }
        this.customerRepository.delete(customerOptional.get());
    }
}
