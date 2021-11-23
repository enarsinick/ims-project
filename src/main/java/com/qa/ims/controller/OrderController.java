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

public class OrderController implements CrudController<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private Utils utils;
	private OrderDAO orderDAO;
	private CustomerController customers;
	private ProductController products;
	
	
	public OrderController(Utils utils, OrderDAO orderDAO, CustomerController customerController, ProductController products) {
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
		List<Order> orders = orderDAO.readAll();
		orders.stream().forEach(order -> LOGGER.info(order));
		return orders;
	}
	
	/*
	 * Creates a new order that is tied to an existing customer
	 */

	@Override
	public Order create() {
		// First we need to list all customers and get ID 
		List<Customer> customers = this.customers.readAll();
		customers.stream().forEach(customer -> LOGGER.info(customer));
		LOGGER.info("Please select the ID of the customer you'd like to create an order for");
		Long customerId = utils.getLong();
		
		// List all products in system and get choices
		List<Product> products = this.products.readAll();
		products.stream().forEach(product -> LOGGER.info(product));
		Map<Long, Long> chosenProds = new HashMap<>();
		boolean choosing = true;
		
		// Get users choices and quantity
		while (choosing) {
			LOGGER.info("Please supply ID of product to add or write 0 when finished");
			Long id = utils.getLong();
			if (id != 0) {
				LOGGER.info("Please supply the quantity");
				Long quantity = utils.getLong();
				chosenProds.put(id, quantity);
			} else {
				choosing = false;
				break;
			}
		} 
		
		// Create an order
		Order order = orderDAO.create(new Order(customerId));
		
		// Create entries in order items
		for (Entry<Long, Long> i : chosenProds.entrySet()) {
			orderDAO.createOrderItem(i.getKey(), i.getValue(), order.getOrderId(), order.getCustomerId());
		}
		
		return order;
	}

	@Override
	public Order update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		readAll();
		LOGGER.info("Please enter the ID of the order you'd like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}
