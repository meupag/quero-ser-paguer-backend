package com.customer.service.customer.integration.aspect;

import com.customer.service.config.RabbitMQConfig;
import com.customer.service.customer.document.Customer;
import com.customer.service.customer.integration.request.CustomerEventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Event Aspect test")
public class CustomerEventAspectTest {

    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private RabbitMQConfig rabbitMQConfig;

    private CustomerEventAspect customerEventAspect;

    @BeforeEach
    public void before() {
        this.customerEventAspect = new CustomerEventAspect(this.rabbitTemplate, this.rabbitMQConfig, new ObjectMapper());
    }

    @Test
    @DisplayName("Should test send event save")
    public void shouldTestSendEventSave() throws JsonProcessingException {
        when(this.rabbitMQConfig.getQueueName())
                .thenReturn("event-customer");
        doNothing()
                .when(this.rabbitTemplate).convertAndSend(anyString(), any(Object.class));

        this.customerEventAspect.sendEventSave(getCustomer());

        verify(this.rabbitTemplate, atLeastOnce()).convertAndSend(eq("event-customer"), any(Object.class));
    }

    @Test
    @DisplayName("Should test send event update")
    public void shouldTestSendEventUpdate() throws JsonProcessingException {
        when(this.rabbitMQConfig.getQueueName())
                .thenReturn("event-customer");
        doNothing()
                .when(this.rabbitTemplate).convertAndSend(anyString(), any(Object.class));

        this.customerEventAspect.sendEventUpdate(getCustomer());

        verify(this.rabbitTemplate, atLeastOnce()).convertAndSend(eq("event-customer"), any(Object.class));
    }

    @Test
    @DisplayName("Should test send event delete")
    public void shouldTestSendEventDelete() throws JsonProcessingException {
        when(this.rabbitMQConfig.getQueueName())
                .thenReturn("event-customer");
        doNothing()
                .when(this.rabbitTemplate).convertAndSend(anyString(), any(Object.class));

        this.customerEventAspect.sendEventDelete("5ce16d6de0cb44053231aa1e");

        verify(this.rabbitTemplate, atLeastOnce()).convertAndSend(eq("event-customer"), any(Object.class));
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
