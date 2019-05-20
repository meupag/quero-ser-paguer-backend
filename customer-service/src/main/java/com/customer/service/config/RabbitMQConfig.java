package com.customer.service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private String queueName;

    public RabbitMQConfig(@Value("${customer.queue.name}") String queueName) {
        this.queueName = queueName;
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    public String getQueueName() {
        return queueName;
    }
}
