package com.customer.service.customer.service.impl;

import com.customer.service.customer.document.Customer;
import com.customer.service.customer.exception.CustomerAlreadyExistException;
import com.customer.service.customer.exception.CustomerNotFoundException;
import com.customer.service.customer.exception.CustomerPreConditionException;
import com.customer.service.customer.exception.InvalidCustomerDocumentException;
import com.customer.service.customer.repository.CustomerRepository;
import com.customer.service.customer.service.CustomerService;
import com.customer.service.customer.validator.impl.DocumentValidatorImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service test")
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void before() {
        this.customerService = new CustomerServiceImpl(new DocumentValidatorImpl(), this.customerRepository);
    }

    @Test
    @DisplayName("Should throw InvalidDocumentException")
    public void shouldThrowInvalidDocumentException() {
        Customer customer = Customer.builder()
                .name("jhow")
                .document("1")
                .build();

        assertThrows(InvalidCustomerDocumentException.class, () -> this.customerService.save(customer));

        verify(this.customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw CustomerAlreadyExistException in save")
    public void shouldThrowCustomerAlreadyExistExceptionInSave() {
        when(this.customerRepository.findByDocument(anyString()))
                .thenReturn(Optional.of(Customer.builder().build()));

        Customer customer = Customer.builder()
                .name("jhow")
                .document("26988305068")
                .build();

        assertThrows(CustomerAlreadyExistException.class, () -> this.customerService.save(customer));

        verify(this.customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should test save customer")
    public void shouldTestSave() {
        when(this.customerRepository.findByDocument(anyString()))
                .thenReturn(Optional.empty());
        when(this.customerRepository.save(any()))
                .thenReturn(Customer.builder()
                        .id("1")
                        .build());

        Customer customer = Customer.builder()
                .name("jhow")
                .document("26988305068")
                .build();
        this.customerService.save(customer);

        ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
        verify(this.customerRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException")
    public void shouldThrowCustomerNotFoundException() {
        when(this.customerRepository.findAll())
                .thenReturn(Lists.newArrayList());

        assertThrows(CustomerNotFoundException.class, () -> this.customerService.findAll());
    }

    @Test
    @DisplayName("Should test find all customer")
    public void shouldTestFindAll() {
        when(this.customerRepository.findAll())
                .thenReturn(Lists.newArrayList(getCustomer()));

        List<Customer> customers = this.customerService.findAll();

        assertThat(customers.size(), is(1));
        assertThat(customers.get(0).getId(), is(getCustomer().getId()));
        assertThat(customers.get(0).getName(), is(getCustomer().getName()));
        assertThat(customers.get(0).getDocument(), is(getCustomer().getDocument()));
        assertThat(customers.get(0).getBirthDate(), is(getCustomer().getBirthDate()));
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException in find by id")
    public void shouldThrowCustomerNotFoundExceptionInFindById() {
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> this.customerService.findById("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test find by id")
    public void shouldTestFindById() {
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.of(getCustomer()));

        Customer customer = this.customerService.findById("5ce16d6de0cb44053231aa1e");
        assertThat(customer.getId(), is(getCustomer().getId()));
        assertThat(customer.getName(), is(getCustomer().getName()));
        assertThat(customer.getDocument(), is(getCustomer().getDocument()));
        assertThat(customer.getBirthDate(), is(getCustomer().getBirthDate()));
    }

    @Test
    @DisplayName("Should throw CustomerPreConditionException in update")
    public void shouldThrowCustomerPreConditionExceptionInUpdate(){
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CustomerPreConditionException.class, () -> this.customerService.updateById("5ce16d6de0cb44053231aa1e", getCustomer()));

        verify(this.customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw CustomerAlreadyExistException in update")
    public void shouldThrowCustomerAlreadyExistExceptionInUpdate(){
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.of(getCustomer()));
        when(this.customerRepository.findByDocument(anyString()))
                .thenReturn(Optional.of(getCustomer()));

        assertThrows(CustomerAlreadyExistException.class, () -> this.customerService.updateById("5ce16d6de0cb44053231aa11", getCustomer()));

        verify(this.customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should test update customer")
    public void shouldTestUpdateCustomer(){
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.of(getCustomer()));
        when(this.customerRepository.findByDocument(anyString()))
                .thenReturn(Optional.of(getCustomer()));

        this.customerService.updateById("5ce16d6de0cb44053231aa1e", getCustomer());

        ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
        verify(this.customerRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should Throw CustomerPreConditionException in delete")
    public void shouldThrowCustomerPreConditionExceptionInDelete(){
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CustomerPreConditionException.class, () -> this.customerService.deleteById("5ce16d6de0cb44053231aa1e"));

        verify(this.customerRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should test delete by id")
    public void shouldTestDeleteById(){
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.of(getCustomer()));

        this.customerService.deleteById("5ce16d6de0cb44053231aa1e");

        verify(this.customerRepository, atLeastOnce()).delete(any());
    }

    private Customer getCustomer() {
        return Customer.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .name("John")
                .document("21919402004")
                .birthDate(LocalDate.now())
                .build();
    }
}
