package com.store.controller;

import com.store.api.CustomerApi;
import com.store.domain.Customer;
import com.store.exception.PostWithIdException;
import com.store.message.ExceptionMessage;
import com.store.resource.CustomerResource;
import com.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CustomerResource> addCustomer(Customer customer) {
        if (customer.getId() != null) {
            throw new PostWithIdException(ExceptionMessage.PostWithId);
        }

        Customer persistedCustomer = customerService.createNewCustomer(customer);
        return new ResponseEntity<>(new CustomerResource(persistedCustomer), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerResource> getCustomerById(@PathVariable Integer customerId) {
        Customer customer = customerService.findCustomer(customerId);
        return ResponseEntity.ok(new CustomerResource(customer));
    }

    @Override
    public ResponseEntity<PagedResources<CustomerResource>> getCustomer(Integer customerId, String name, String cpf, LocalDate birthDate, Pageable pageable, PagedResourcesAssembler assembler) {
        Customer customerExample = Customer.builder().id(customerId).name(name).cpf(cpf).birthDate(birthDate).build();
        Page<Customer> customerPage = customerService.findCustomerByExample(customerExample, pageable);
        return new ResponseEntity<PagedResources<CustomerResource>>(assembler.toResource(customerPage), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResource> updateCustomerById(Integer customerId, Customer customer) {
        Customer updatedCustomer = customerService.updateById(customerId, customer);
        return ResponseEntity.ok(new CustomerResource(updatedCustomer));
    }

    @Override
    public ResponseEntity<Void> deleteCustomerById(Integer customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
