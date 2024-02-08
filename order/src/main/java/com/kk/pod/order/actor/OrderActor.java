package com.kk.pod.order.actor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kk.pod.order.holder.Order;
import com.kk.pod.order.message.CreateOrderMessage;
import com.kk.pod.order.message.GetOrderMessage;
import com.kk.pod.order.utils.OrderUtils;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class OrderActor extends AbstractActor {

	private final String dbUrl;
	private final String dbUser;
	private final String dbPassword;

	public OrderActor() {
		Config config = ConfigFactory.load();
		this.dbUrl = config.getString("database.url");
		this.dbUser = config.getString("database.user");
		this.dbPassword = config.getString("database.password");
	}

	public Receive createReceive() {
		return receiveBuilder().match(CreateOrderMessage.class, this::handleCreateOrder)
				.match(GetOrderMessage.class, this::handleGetOrder).build();
	}

	private void handleCreateOrder(CreateOrderMessage message) {
		try (Connection connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword)) {
			// Implement logic to create an order and store it
			String insertQuery = "INSERT INTO orders (order_id, order_data) VALUES (?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				preparedStatement.setString(1, message.getOrderId());
				// Assuming you have a method to convert the order object to JSON
				preparedStatement.setString(2, OrderUtils.convertOrderToJson(message.getOrder()));
				preparedStatement.executeUpdate();
			}
			System.out.println("Order created: " + message.getOrderId());
			System.out.println("Order created: " + message.getOrder());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleGetOrder(GetOrderMessage message) {
		try (Connection connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword)) {
			String selectQuery = "SELECT order_data FROM orders WHERE order_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setString(1, message.getOrderId());
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String orderData = resultSet.getString("order_data");
						// Assuming you have a method to convert JSON to the order object
						Order order = OrderUtils.convertJsonToOrder(orderData);
						System.out.println("Order retrieved for ID: " + message.getOrderId() + ", Order: " + order);
					} else {
						System.out.println("Order not found for ID: " + message.getOrderId());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Static props() method for creating the actor's Props
	public static Props props() {
		return Props.create(OrderActor.class);
	}
}
