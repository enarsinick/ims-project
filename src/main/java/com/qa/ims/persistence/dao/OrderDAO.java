package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT "
						+ "order_id, "
						+ "first_name, "
						+ "last_name, "
						+ "title, "
						+ "price, "
						+ "order_quantity, "
						+ "order_quantity*price AS total "
						+ "FROM order_items oi "
						+ "JOIN products p ON oi.fk_product_id=p.product_id "
						+ "JOIN orders o ON oi.fk_order_id=o.order_id "
						+ "JOIN customers c ON o.fk_customer_id=c.customer_id");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(orderItemFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	@Override
	public Order read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Reads the most recent order from DB and returns said order
	 */
	
	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates a new order in the orders table
	 * 
	 * @param order - takes in a order object.
	 */

	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
				.prepareStatement("INSERT INTO orders (fk_customer_id) VALUES (?)");) {
				statement.setLong(1, order.getCustomerId());
				statement.executeUpdate();
				LOGGER.info("Order created");
				return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates a new order in the order_items table
	 */
	
	public void createOrderItem(Long product, Long quantity, Long orderId, Long customerId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
				.prepareStatement("INSERT INTO order_items (fk_order_id, fk_product_id, fk_customer_id, order_quantity) VALUES (? , ?, ? , ?)");) {
				statement.setLong(1, orderId);
				statement.setLong(2, product);
				statement.setLong(3, customerId);
				statement.setLong(4, quantity);
				statement.executeUpdate();
				LOGGER.info("Item(s) added...");
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public Order update(Order t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Deletes an order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?");) {
			statement.setLong(1, id);
			LOGGER.info("Order has been deleted...");			
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}
	
	/*
	 * Returns a single order with no products attached, just order ID and customer ID
	 */

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long customerId = resultSet.getLong("fk_customer_id");
		Long orderId = resultSet.getLong("order_id");
		return new Order(orderId, customerId);
	}
	
	/**
	 * Returns a new order object
	 */
	public Order orderItemFromResultSet(ResultSet resultSet) throws SQLException {
		Long orderId = resultSet.getLong("order_id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String title = resultSet.getString("title");
		double price = resultSet.getDouble("price");
		Long quantity = resultSet.getLong("order_quantity");
		double total = resultSet.getDouble("total");
		return new Order(orderId, firstName, lastName, title, price, quantity, total);
	}
	
	

}
