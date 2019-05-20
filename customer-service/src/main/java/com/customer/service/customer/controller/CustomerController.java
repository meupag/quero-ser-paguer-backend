package com.customer.service.customer.controller;

import com.customer.service.customer.document.Customer;
import com.customer.service.customer.service.CustomerService;
import com.swagger.customer.service.api.CustomerApi;
import com.swagger.customer.service.model.CustomerRequest;
import com.swagger.customer.service.model.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<Void> save(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .document(customerRequest.getDocument())
                .birthDate(customerRequest.getBirthDate())
                .build();
        this.customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<Customer> customers = this.customerService.findAll();
        List<CustomerResponse> customersResponse = customers.stream()
                .map(c -> new CustomerResponse()
                        .id(c.getId())
                        .name(c.getName())
                        .document(c.getDocument())
                        .birthDate(c.getBirthDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customersResponse);
    }

    @Override
    public ResponseEntity<CustomerResponse> findById(String id) {
        Customer customer = this.customerService.findById(id);
        CustomerResponse customerResponse = new CustomerResponse()
                .id(customer.getId())
                .name(customer.getName())
                .document(customer.getDocument())
                .birthDate(customer.getBirthDate());
        return ResponseEntity.ok(customerResponse);
    }

    @Override
    public ResponseEntity<Void> updateById(String id, CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .document(customerRequest.getDocument())
                .birthDate(customerRequest.getBirthDate())
                .build();
        this.customerService.updateById(id, customer);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String id) {
        this.customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}