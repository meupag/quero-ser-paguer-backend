package com.order.service.customer.service.impl;

import com.order.service.customer.document.Customer;
import com.order.service.customer.exception.CustomerPreConditionException;
import com.order.service.customer.repository.CustomerRepository;
import com.order.service.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer service test")
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void before() {
        this.customerService = new CustomerServiceImpl(this.customerRepository);
    }

    @Test
    @DisplayName("Should CustomerPreConditionException")
    public void shouldThrowCustomerPreConditionException() {
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CustomerPreConditionException.class, () -> this.customerService.valid("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test find by id")
    public void shouldTestValid(){
        when(this.customerRepository.findById(anyString()))
            .thenReturn(Optional.of(getCustomer()));

        this.customerService.valid("5ce16d6de0cb44053231aa1e");
    }

    private Customer getCustomer() {
        return Customer.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .name("John")
                .document("21919402004")
                .build();
    }
}
