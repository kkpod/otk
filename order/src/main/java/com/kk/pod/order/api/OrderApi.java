package com.kk.pod.order.api;

import com.kk.pod.order.actor.OrderActor;
import com.kk.pod.order.endpoint.OrderRoutes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;

public class OrderApi {

    public static void main(String[] args) {
        // Create an Akka system
        ActorSystem system = ActorSystem.create("OrderApiSystem");

        // Create an instance of the OrderActor
        ActorRef orderActor = system.actorOf(OrderActor.props(), "orderActor");

        // Create the OrderRoutes
        OrderRoutes orderRoutes = new OrderRoutes(orderActor);

        // Start the HTTP server
        Http.get(system).newServerAt("localhost", 8080).bind(orderRoutes.routes());
        System.out.println("Server started at http://localhost:8080/");
        
        // Your application logic goes here
    }
}
