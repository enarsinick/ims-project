package com.qa.ims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private Utils utils;
	private OrderDAO orderDAO;
	private CustomerController customers;
	private ProductController products;

	public OrderController(Utils utils, OrderDAO orderDAO, CustomerController customerController,
			ProductController products) {
		super();
		this.utils = utils;
		this.orderDAO = orderDAO;
		this.customers = customerController;
		this.products = products;
	}

	/*
	 * View all orders within the system
	 */
	@Override
	public List<Order> readAll() {
		LOGGER.info("---------------------------------------------------");
		List<Order> orders = orderDAO.readAll();
		orders.stream().forEach(order -> LOGGER.info(order));
		return orders;
	}

	/*
	 * Creates a new order that is tied to an existing customer
	 */
	@Override
	public Order create() {
		LOGGER.info("---------------------------------------------------");
		// First we need to list all customers and get ID
		List<Customer> customers = this.customers.readAll();
		customers.stream().forEach(customer -> LOGGER.info(customer));
		LOGGER.info("Please select the ID of the customer you'd like to create an order for");
		Long customerId = utils.getLong();

		// Create an order
		Order order = orderDAO.create(new Order(customerId));
		
		// List all products in system and get choices
		List<Product> products = this.products.readAll();
		products.stream().forEach(product -> LOGGER.info(product));
		Map<Long, Long> chosenProds = getProductChoice();

		// Create entries in order items
		for (Entry<Long, Long> i : chosenProds.entrySet()) {
			orderDAO.createOrderItem(i.getKey(), i.getValue(), order.getOrderId(), order.getCustomerId());
		}

		return order;
	}

	/*
	 * Update or delete items from an order
	 */

	@Override
	public Order update() {
		Long orderId;
		List<Product> products;

		// List all current orders
		readAll();

		// Select the order that needs to be updated and retrieve order from orders
		// table
		LOGGER.info("---------------------------------------------------");
		LOGGER.info("Please write the ID of the order you want to update");
		orderId = utils.getLong();
		Order order = orderDAO.read(orderId);

		// Get contents of entire order from order items table
		products = orderDAO.getOrderContents(order.getOrderId());
		products.stream().forEach(product -> LOGGER.info(product));

		// What does the user want to do
		LOGGER.info("---------------------------------------------------");
		LOGGER.info("Would you like to delete or add a product?");
		String input = utils.getString().toUpperCase();

		// Call methods based on response
		if (input.equals("ADD")) {
			return addToOrder(order);
		} else if (input.equals("DELETE")) {
			return deleteFromOrder(order);
		} else {
			LOGGER.info("You didn't specify a correct action. Please try again.");
		}
		return null;
	}

	/*
	 * Builds a list of products to add to an existing order
	 */
	public Order addToOrder(Order order) {
		// Print all games and get product choices
		this.products.readAll();
		Map<Long, Long> chosenProds = getProductChoice();
		// Create entries in order items
		for (Entry<Long, Long> i : chosenProds.entrySet()) {
			Order newOrderItem = new Order(order.getOrderId(), order.getCustomerId(), i.getKey(), i.getValue());
			return orderDAO.update(newOrderItem);
		}
		return null;
	}

	/*
	 * Deletes an entire order based on it's ID
	 */
	@Override
	public int delete() {
		LOGGER.info("---------------------------------------------------");
		readAll();
		LOGGER.info("Please enter the ID of the order you'd like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

	/*
	 * Delete an item from an existing order
	 */
	public Order deleteFromOrder(Order order) {
		LOGGER.info("Please supply ID of product to delete");
		Long removeItemId = utils.getLong();
		orderDAO.removeFromOrder(removeItemId, order.getCustomerId(), order.getOrderId());
		return order;
	}

	/*
	 * Get choice and quantity of new products
	 */
	public Map<Long, Long> getProductChoice() {
		Map<Long, Long> chosenProds = new HashMap<>();
		// Get users choices and quantity
		LOGGER.info("---------------------------------------------------");
		LOGGER.info("Please supply ID of product to add");
		Long productId = utils.getLong();
		LOGGER.info("Please supply the quantity");
		Long quantity = utils.getLong();
		chosenProds.put(productId, quantity);
		return chosenProds;
	}
}
