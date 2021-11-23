package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long customerId = resultSet.getLong("fk_customer_id");
		Long orderId = resultSet.getLong("order_id");
		return new Order(orderId, customerId);
	}

}
