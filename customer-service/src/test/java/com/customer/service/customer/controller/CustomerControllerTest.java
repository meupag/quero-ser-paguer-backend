package com.customer.service.customer.controller;

import com.customer.service.customer.document.Customer;
import com.customer.service.customer.service.CustomerService;
import com.swagger.customer.service.model.CustomerRequest;
import com.swagger.customer.service.model.CustomerResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer controller test")
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    private CustomerController customerController;

    @BeforeEach
    public void before(){
        this.customerController = new CustomerController(this.customerService);
    }

    @Test
    @DisplayName("Should test save customer")
    public void shouldTestSave(){
        when(this.customerService.save(any()))
                .thenReturn(getCustomer());

        CustomerRequest customerRequest = new CustomerRequest()
                .name("jhow")
                .document("123.123.123-12")
                .birthDate(LocalDate.now());
        ResponseEntity<Void> responseEntity = this.customerController.save(customerRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    @DisplayName("Should test find all customer")
    public void shouldTestFindAll(){
        when(this.customerService.findAll())
                .thenReturn(Lists.newArrayList(getCustomer()));

        ResponseEntity<List<CustomerResponse>> responseEntity = this.customerController.findAll();

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().size(), is(1));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().get(0).getId(), is(getCustomer().getId()));
        assertThat(responseEntity.getBody().get(0).getName(), is(getCustomer().getName()));
        assertThat(responseEntity.getBody().get(0).getDocument(), is(getCustomer().getDocument()));
        assertThat(responseEntity.getBody().get(0).getBirthDate(), is(getCustomer().getBirthDate()));
    }

    @Test
    @DisplayName("Should test find by id customer")
    public void shouldTestFindById(){
        when(this.customerService.findById(anyString()))
                .thenReturn(getCustomer());

        ResponseEntity<CustomerResponse> responseEntity = this.customerController.findById("5ce16d6de0cb44053231aa1e");

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getId(), is(getCustomer().getId()));
        assertThat(responseEntity.getBody().getName(), is(getCustomer().getName()));
        assertThat(responseEntity.getBody().getDocument(), is(getCustomer().getDocument()));
        assertThat(responseEntity.getBody().getBirthDate(), is(getCustomer().getBirthDate()));
    }

    @Test
    @DisplayName("Should test update customer")
    public void shouldTestUpdateById(){
        when(this.customerService.updateById(anyString(), any()))
                .thenReturn(getCustomer());

        CustomerRequest customerRequest = new CustomerRequest()
                .name("jhow")
                .document("123.123.123-12")
                .birthDate(LocalDate.now());
        ResponseEntity<Void> responseEntity = this.customerController.updateById("5ce16d6de0cb44053231aa1e", customerRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("Should test delete customer")
    public void shouldTestDeleteById(){
        doNothing()
                .when(this.customerService).deleteById(anyString());

        ResponseEntity<Void> responseEntity = this.customerController.deleteById("5ce16d6de0cb44053231aa1e");

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    private Customer getCustomer(){
        return Customer.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .name("John")
                .document("21919402004")
                .birthDate(LocalDate.now())
                .build();
    }
}