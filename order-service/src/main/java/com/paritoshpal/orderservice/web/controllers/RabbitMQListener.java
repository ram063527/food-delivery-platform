package com.paritoshpal.orderservice.web.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handleNewOrder(MyPayload payload){
        System.out.println("Received new order: " + payload.content());
    }

    @RabbitListener(queues = "${orders.confirmed-orders-queue}")
    public void handleConfirmedOrder(MyPayload payload){
        System.out.println("Received confirmed order: " + payload.content());
    }

}
