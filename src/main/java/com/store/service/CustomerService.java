package com.store.service;

import com.store.domain.AccountStatus;
import com.store.domain.Customer;
import com.store.exception.CpfAlreadyUsedException;
import com.store.exception.ResourceNotFoundException;
import com.store.message.ExceptionMessage;
import com.store.repository.CustomerRepository;
import com.store.util.PropertiesCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PropertiesCopier copier;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PropertiesCopier copier) {
        this.customerRepository = customerRepository;
        this.copier = copier;
    }

    public Customer createNewCustomer(Customer customer) {
        customer.setAccountStatus(AccountStatus.ACTIVE);
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new CpfAlreadyUsedException(ExceptionMessage.CpfAlreadyUsed);
        }
    }

    public Customer findCustomer(Integer customerId) {
        return findCustomerById(customerId);
    }

    public Page<Customer> findCustomerByExample(Customer customer, Pageable pageable) {
        return customerRepository.findAll(Example.of(customer), pageable);
    }

    public Customer updateById(Integer customerId, Customer customer) {
        customer.setId(customerId);
        Customer persisted = findCustomerById(customerId);
        copier.copyProperties(customer, persisted);
        return persisted;
    }

    public void deleteById(Integer customerId) {
        Customer customer = findCustomerById(customerId);
        customer.setAccountStatus(AccountStatus.INACTIVE);
    }

    private Customer findCustomerById(Integer customerId) throws ResourceNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.CustomerNotFound));
    }

}
