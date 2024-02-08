package com.kk.pod.order.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.pod.order.holder.Order;

public class OrderUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String convertOrderToJson(Order order) {
		try {
			return objectMapper.writeValueAsString(order);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting Order to JSON", e);
		}
	}

	public static Order convertJsonToOrder(String json) {
		try {
			return objectMapper.readValue(json, Order.class);
		} catch (Exception e) {
			throw new RuntimeException("Error converting JSON to Order", e);
		}
	}

}
