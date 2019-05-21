package com.customer.service.customer.integration.aspect;

import com.customer.service.config.RabbitMQConfig;
import com.customer.service.customer.document.Customer;
import com.customer.service.customer.integration.request.CustomerEventRequest;
import com.customer.service.customer.integration.request.CustomerEventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
class CustomerEventAspect {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final ObjectMapper objectMapper;

    public CustomerEventAspect(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.objectMapper = objectMapper;
    }

    @AfterReturning(pointcut = "execution(* com.customer.service.customer.service.CustomerService.save(..))",
            returning = "customer")
    public void sendEventSave(Customer customer) throws JsonProcessingException {
        CustomerEventRequest customerEventRequest = CustomerEventRequest.builder()
                .customer(customer)
                .customerEventType(CustomerEventType.SAVE)
                .build();

        this.rabbitTemplate.convertAndSend(this.rabbitMQConfig.getQueueName(), this.objectMapper.writeValueAsString(customerEventRequest));
    }

    @AfterReturning(pointcut = "execution(* com.customer.service.customer.service.CustomerService.updateById(..))",
            returning = "customer")
    public void sendEventUpdate(Customer customer) throws JsonProcessingException {
        CustomerEventRequest customerEventRequest = CustomerEventRequest.builder()
                .customer(customer)
                .customerEventType(CustomerEventType.UPDATE)
                .build();

        this.rabbitTemplate.convertAndSend(this.rabbitMQConfig.getQueueName(), this.objectMapper.writeValueAsString(customerEventRequest));
    }

    @AfterReturning(pointcut = "execution(* com.customer.service.customer.service.CustomerService.deleteById(..)) && args(id)")
    public void sendEventDelete(String id) throws JsonProcessingException {
        CustomerEventRequest customerEventRequest = CustomerEventRequest.builder()
                .customer(Customer.builder()
                        .id(id)
                        .build())
                .customerEventType(CustomerEventType.DELETE)
                .build();

        this.rabbitTemplate.convertAndSend(this.rabbitMQConfig.getQueueName(), this.objectMapper.writeValueAsString(customerEventRequest));
    }
}
