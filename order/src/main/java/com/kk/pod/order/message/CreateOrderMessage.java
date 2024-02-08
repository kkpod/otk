package com.kk.pod.order.message;

import java.io.Serializable;

import com.kk.pod.order.holder.Order;

public class CreateOrderMessage implements Serializable {
	private String orderId;
	private Order order;

	public CreateOrderMessage() {
		// Default constructor required for Jackson
	}

	public CreateOrderMessage(String orderId) {
		this.orderId = orderId;
	}

	public CreateOrderMessage(String orderId, Order order) {
		this.order = order;
	}

	public String getOrderId() {
		return orderId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
