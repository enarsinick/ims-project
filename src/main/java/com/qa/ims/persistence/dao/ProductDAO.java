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

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class ProductDAO implements Dao<Product> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * Reads the most recent product to be added to the database
	 */
	public Product readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM products ORDER BY product_id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates a product in the database
	 * 
	 * @param product - takes in a product object. id will be ignored
	 */
	@Override
	public Product create(Product product) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO products(title, price) VALUES (?, ?)");) {
			statement.setString(1, product.getTitle());
			statement.setDouble(2, product.getPrice());
			statement.executeUpdate();
			LOGGER.info("Product created");
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	

	@Override
	public Product modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long productId = resultSet.getLong("product_id");
		String title = resultSet.getString("title");
		double price = resultSet.getDouble("price");
		return new Product(productId, title, price);
	}

	/**
	 * Reads all products from the database
	 * 
	 * @return A list of products
	 */
	
	@Override
	public List<Product> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM products");) {
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				products.add(modelFromResultSet(resultSet));
			}
			return products;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	@Override
	public Product read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Updates a product in the database
	 * 
	 * @param product - takes in a product object, the id field will be used to
	 *                 update that product in the database
	 * @return
	 */
	@Override
	public Product update(Product product) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE products SET title = ?, price = ? WHERE product_id = ?");) {
			statement.setString(1, product.getTitle());
			statement.setDouble(2, product.getPrice());
			statement.setLong(3, product.getProductId());
			statement.executeUpdate();
			LOGGER.info("Product updated: " + product);
			return read(product.getProductId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
