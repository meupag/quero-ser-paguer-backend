package com.store.service;

import com.store.AbstractTest;
import com.store.domain.AccountStatus;
import com.store.domain.Customer;
import com.store.exception.ResourceNotFoundException;
import com.store.repository.CustomerRepository;
import com.store.util.PropertiesCopier;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = CustomerService.class)
public class CustomerServiceTest extends AbstractTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PropertiesCopier copier;

    private Customer customerVerification;
    private Customer customerTest;

    private Integer wrongCustomerId;

    @Before
    public void setup() {
        super.setup();
        customerVerification = setupCustomer();
        customerTest = customerVerification.toBuilder().build();
        wrongCustomerId = customerId + 1;

        given(customerRepository.findById(customerId)).willReturn(Optional.ofNullable(customerTest));
    }

    @Test
    public void whenSaveCustomerReturnActiveCustomer_thenAssertionSucceeds() {
        customerTest.setAccountStatus(AccountStatus.INACTIVE);
        given(customerRepository.save(customerTest)).willReturn(customerTest);

        Customer persisted = customerService.createNewCustomer(customerTest);
        verifyCustomer(persisted);
    }

    @Test
    public void whenFindCustomer_thenAssertionSucceeds() {
        Customer persisted = customerService.findCustomer(customerId);
        verifyCustomer(persisted);
    }

    @Test
    public void whenFindCustomerByExample_thenAssertionSucceeds() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customerTest);
        Page<Customer> pageCustomer = new PageImpl<>(customerList);
        given(customerRepository.findAll(Example.of(customerTest), Pageable.unpaged())).willReturn(pageCustomer);

        Page<Customer> persisted = customerService.findCustomerByExample(customerTest, Pageable.unpaged());
        verifyCustomer(persisted.getContent().get(0));
    }

    @Test
    public void whenUpdateCustomer_thenAssertionSucceeds() {
        Customer persisted = customerService.updateById(customerId, customerTest);
        verifyCustomer(persisted);
    }

    @Test
    public void whenDeleteCustomer_thenAssertionSucceeds() {
        customerService.deleteById(customerId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindCustomerNotFound_thenResourceNotFoundException() {
        customerService.findCustomer(wrongCustomerId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdateCustomerNotFound_thenResourceNotFoundException() {
        customerService.updateById(wrongCustomerId, customerTest);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeleteCustomerNotFound_thenResourceNotFoundException() {
        customerService.deleteById(wrongCustomerId);
    }

    private void verifyCustomer(Customer customerToVerify) {
        assertEquals(customerToVerify.getId(), customerVerification.getId());
        assertEquals(customerToVerify.getName(), customerVerification.getName());
        assertEquals(customerToVerify.getCpf(), customerVerification.getCpf());
        assertEquals(customerToVerify.getBirthDate(), customerVerification.getBirthDate());
        assertEquals(customerToVerify.getAccountStatus(), customerVerification.getAccountStatus());
    }

}
