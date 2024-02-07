package com.kk.pod.order.endpoint;

import com.kk.pod.order.message.CreateOrderMessage;
import com.kk.pod.order.message.GetOrderMessage;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;

//Define routes for handling HTTP requests
public class OrderRoutes extends AllDirectives {

	private final ActorRef orderActor;

	public OrderRoutes(ActorRef orderActor) {
		this.orderActor = orderActor;
	}

	public Route routes() {
		return path("order", () -> post(() -> entity(Jackson.unmarshaller(CreateOrderMessage.class), order -> {
			orderActor.tell(order, ActorRef.noSender());
            System.out.println("Received order creation request: " + order);
			return complete("Order created successfully");
		}))).orElse(path(PathMatchers.segment("order").slash(PathMatchers.segment()), orderId -> get(() -> {
			orderActor.tell(new GetOrderMessage(orderId), ActorRef.noSender());
            System.out.println("Received order retrieval request for ID: " + orderId);
			return complete("Order retrieved successfully");
		})));
	}

}
