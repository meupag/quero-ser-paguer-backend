package com.order.service.customer.receiver;

import com.order.service.customer.event.CustomerEventChain;
import com.order.service.customer.receiver.request.CustomerEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class CustomerEventReceiver {

    private final ObjectMapper objectMapper;
    private final List<CustomerEventChain> customerEventsChain;

    public CustomerEventReceiver(ObjectMapper objectMapper, List<CustomerEventChain> customerEventsChain) {
        this.objectMapper = objectMapper;
        this.customerEventsChain = customerEventsChain;
    }

    @RabbitListener(queues = {"${customer.queue.name}"})
    public void receiveMessage(String message) throws IOException {
        log.info("Customer payload = {}", message);

        CustomerEventRequest customerEventRequest = this.objectMapper.readValue(message, CustomerEventRequest.class);

        this.customerEventsChain.forEach(c -> {
            if (c.canHandle(customerEventRequest)) {
                c.execute(customerEventRequest);
            }
        });
    }
}
