package com.order.service.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.service.customer.document.Customer;
import com.order.service.customer.event.CustomerEventChain;
import com.order.service.customer.event.impl.CustomerEventDelete;
import com.order.service.customer.event.impl.CustomerEventSave;
import com.order.service.customer.event.impl.CustomerEventUpdate;
import com.order.service.customer.exception.CustomerEventNotFoundException;
import com.order.service.customer.receiver.CustomerEventReceiver;
import com.order.service.customer.receiver.request.CustomerEventRequest;
import com.order.service.customer.receiver.request.CustomerEventType;
import com.order.service.customer.repository.CustomerRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer event receiver test")
public class CustomerEventReceiverTest {

    @Mock
    private CustomerRepository customerRepository;

    private List<CustomerEventChain> customerEventsChain;

    private CustomerEventReceiver customerEventReceiver;

    @BeforeEach
    public void before() {
        this.customerEventsChain = Lists.newArrayList(
                new CustomerEventDelete(this.customerRepository),
                new CustomerEventSave(this.customerRepository),
                new CustomerEventUpdate(this.customerRepository));
        this.customerEventReceiver = new CustomerEventReceiver(new ObjectMapper(), this.customerEventsChain);
    }

    @Test
    @DisplayName("Should test receive message delete")
    public void shouldTestReceiveMessageDelete() throws IOException {
        String json = new ObjectMapper().writeValueAsString(getCustomerEventRequest(CustomerEventType.DELETE));
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.of(getCustomer()));

        this.customerEventReceiver.receiveMessage(json);

        verify(this.customerRepository, atLeastOnce()).delete(any());
        verify(this.customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw CustomerEventNotFoundException in delete")
    public void shouldThrowCustomerEventNotFoundExceptionInDelete() throws IOException {
        String json = new ObjectMapper().writeValueAsString(getCustomerEventRequest(CustomerEventType.DELETE));
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CustomerEventNotFoundException.class, () -> this.customerEventReceiver.receiveMessage(json));

        verify(this.customerRepository, never()).delete(any());
        verify(this.customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should test receive message update")
    public void shouldTestReceiveMessageUpdate() throws IOException {
        String json = new ObjectMapper().writeValueAsString(getCustomerEventRequest(CustomerEventType.UPDATE));
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.of(getCustomer()));

        this.customerEventReceiver.receiveMessage(json);

        verify(this.customerRepository, atLeastOnce()).save(any());
        verify(this.customerRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should throw CustomerEventNotFoundException in update")
    public void shouldThrowCustomerEventNotFoundExceptionInUpdate() throws IOException {
        String json = new ObjectMapper().writeValueAsString(getCustomerEventRequest(CustomerEventType.UPDATE));
        when(this.customerRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CustomerEventNotFoundException.class, () -> this.customerEventReceiver.receiveMessage(json));

        verify(this.customerRepository, never()).save(any());
        verify(this.customerRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should test receive message save")
    public void shouldTestReceiveMessageSave() throws IOException {
        String json = new ObjectMapper().writeValueAsString(getCustomerEventRequest(CustomerEventType.SAVE));

        this.customerEventReceiver.receiveMessage(json);

        verify(this.customerRepository, atLeastOnce()).save(any());
        verify(this.customerRepository, never()).delete(any());
    }

    private CustomerEventRequest getCustomerEventRequest(CustomerEventType customerEventType){
        return CustomerEventRequest.builder()
                .customerEventType(customerEventType)
                .customer(getCustomer())
                .build();
    }

    private Customer getCustomer() {
        return Customer.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .name("John")
                .document("21919402004")
                .build();
    }
}
