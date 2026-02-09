package com.paritoshpal.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paritoshpal.orderservice.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final ApplicationProperties properties;


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(properties.orderEventsExchange());
    }

    // Define a Queue bean
    @Bean
    Queue newOrdersQueue(){
        return QueueBuilder.durable(properties.newOrdersQueue()).build();
    }
    // Bind the Queue to the Exchange with a routing key
    // We are using routing keys that are the same as the queue names for simplicity, but in a real application, you might want to use different routing keys.
    // for example : here we are using new-orders as routing key for new-orders queue, but we could have used something like order.new or order.created as routing key and then bind it to the queue.
    @Bean
    Binding newOrdersQueueBinding(){
        return BindingBuilder.bind(newOrdersQueue()).to(exchange()).with(properties.newOrdersQueue());
    }

    @Bean
    Queue confirmedOrdersQueue(){
        return QueueBuilder.durable(properties.confirmedOrdersQueue()).build();
    }
    // Bind the Confirmed Orders Queue to the Exchange with a routing key

    @Bean
    Binding confirmedOrdersQueueBinding(){
        return BindingBuilder.bind(confirmedOrdersQueue()).to(exchange()).with(properties.confirmedOrdersQueue());
    }


    @Bean
    Queue cancelledOrdersQueue(){
        return QueueBuilder.durable(properties.cancelledOrdersQueue()).build();
    }
    // Bind the Cancelled Orders Queue to the Exchange with a routing key

    @Bean
    Binding cancelledOrdersQueueBinding(){
        return BindingBuilder.bind(cancelledOrdersQueue()).to(exchange()).with(properties.cancelledOrdersQueue());
    }

    @Bean
    Queue errorOrdersQueue() {
        return QueueBuilder.durable(properties.errorOrdersQueue()).build();
    }

    // Bind the Error Orders Queue to the Exchange with a routing key

    @Bean
    Binding errorOrdersQueueBinding() {
        return BindingBuilder.bind(errorOrdersQueue()).to(exchange()).with(properties.errorOrdersQueue());
    }


    // Template for Sending Message to RabbitMQ

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }




}
