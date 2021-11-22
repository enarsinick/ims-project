package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;

public class ProductController implements CrudController<Product> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private ProductDAO productDAO;
	private Utils utils;
	
	public ProductController(ProductDAO productDAO, Utils utils) {
		super();
		this.productDAO = productDAO;
		this.utils = utils;
	}

	@Override
	public List<Product> readAll() {
		List<Product> products = productDAO.readAll();
		products.stream().forEach(product -> LOGGER.info(product));
		return products;
	}
	
	/**
	 * Creates a product by taking in user input
	 */
	@Override
	public Product create() {
		LOGGER.info("Please enter a title of the game");
		String title = utils.getString();
		LOGGER.info("Please enter a price for the game (Example 19.99)");
		double price = utils.getDouble();
		Product product = productDAO.create(new Product(title, price));
		return product;
	}
	
	/**
	 * Updates an existing product by taking in user input
	 */
	@Override
	public Product update() {
		LOGGER.info("Please enter the id of the product you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter the title for the game");
		String title = utils.getString();
		LOGGER.info("Please enter a new price for the game (e.g 19.99)");
		double price = utils.getDouble();
		Product product = productDAO.update(new Product(id, title, price));
		return product;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the ID of the product you'd like to delete");
		Long id = utils.getLong();
		return productDAO.delete(id);
	}

}
