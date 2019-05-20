package com.customer.service.customer.service;

import com.customer.service.customer.document.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(String id);

    Customer updateById(String id, Customer customer);

    void deleteById(String id);
}
