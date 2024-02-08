package com.kk.pod.order.message;

import java.io.Serializable;

//Message for retrieving an order
public class GetOrderMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;

	public GetOrderMessage(String orderId) {
		this.setOrderId(orderId);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
