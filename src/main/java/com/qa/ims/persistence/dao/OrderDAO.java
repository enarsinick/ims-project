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
import com.qa.ims.persistence.domain.Product;
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
						+ "SUM(order_quantity*price) AS total "
						+ "FROM order_items oi "
						+ "JOIN products p ON oi.fk_product_id=p.product_id "
						+ "JOIN orders o ON oi.fk_order_id=o.order_id "
						+ "JOIN customers c ON o.fk_customer_id=c.customer_id "
						+ "GROUP BY order_id");) {
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
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE order_id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
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
	
	public boolean createOrderItem(Long product, Long quantity, Long orderId, Long customerId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
				.prepareStatement("INSERT INTO order_items (fk_order_id, fk_product_id, fk_customer_id, order_quantity) VALUES (? , ?, ? , ?)");) {
				statement.setLong(1, orderId);
				statement.setLong(2, product);
				statement.setLong(3, customerId);
				statement.setLong(4, quantity);
				statement.executeUpdate();
				LOGGER.info("Item(s) added...");
				return true;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Gets the contents of an entire single order
	 * 
	 * @param order id
	 * 
	 * @return List of order contents as product objects
	 */
	
	public List<Product> getOrderContents(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT "
						+ "product_id, "
						+ "title, "
						+ "price "
						+ "FROM order_items oi "
						+ "JOIN products p ON oi.fk_product_id=p.product_id "
						+ "JOIN orders o ON oi.fk_order_id=o.order_id "
						+ "JOIN customers c ON o.fk_customer_id=c.customer_id "
						+ "WHERE order_id = " + id);) {
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				products.add(productFromResultSet(resultSet));
			}
			return products;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	@Override
	public Order update(Order order) {
		createOrderItem(order.getProductId(), order.getQuantity(), order.getOrderId(), order.getCustomerId());
		return null;
	}
	
	/*
	 * Removes a single item from a current order in the order_items table
	 */
	
	public boolean removeFromOrder(Long productId, Long customerId, Long orderId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM order_items WHERE fk_product_id = ? AND fk_customer_id = ? AND fk_order_id = ?");) {
			statement.setLong(1, productId);
			statement.setLong(2, customerId);
			statement.setLong(3, orderId);
			statement.executeUpdate();
			LOGGER.info("Item has been deleted from order...");
			return true;
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return false;
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
	
	/*
	 * Returns a single product that is attached to a specific order
	 */
	public Product productFromResultSet(ResultSet resultSet) throws SQLException {
		Long productId = resultSet.getLong("product_id");
		String title = resultSet.getString("title");
		double price = resultSet.getDouble("price");
		return new Product(productId, title, price);
	}
	
	/**
	 * Returns a new order object
	 */

	public Order orderItemFromResultSet(ResultSet resultSet) throws SQLException {
		Long orderId = resultSet.getLong("order_id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		double total = resultSet.getDouble("total");
		return new Order(orderId, firstName, lastName, total);
	}
	

}
