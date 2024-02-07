package com.kk.pod.order.actor;

import com.kk.pod.order.message.CreateOrderMessage;
import com.kk.pod.order.message.GetOrderMessage;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class OrderActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(CreateOrderMessage.class, this::handleCreateOrder)
            .match(GetOrderMessage.class, this::handleGetOrder)
            .build();
    }

    private void handleCreateOrder(CreateOrderMessage message) {
        // Implement logic to create an order and store it in DynamoDB
        // You can use AWS SDK for Java to interact with DynamoDB
        // Example: DynamoDBAsyncClient client = DynamoDBAsyncClient.builder().region(Region.US_EAST_1).build();
        System.out.println("Order created: " + message);
    	
    }

    private void handleGetOrder(GetOrderMessage message) {
        // Implement logic to retrieve an order from DynamoDB
        System.out.println("Order retrieved for ID: " + message.getOrderId());
    	
    }

    // Static props() method for creating the actor's Props
    public static Props props() {
        return Props.create(OrderActor.class);
    }
    
    // Define other methods as needed
    
}
