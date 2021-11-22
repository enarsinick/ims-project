package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private Utils utils;
	private OrderDAO orderDAO;
	private CustomerController customers;
	
	
	public OrderController(Utils utils, OrderDAO orderDAO, CustomerController customerController) {
		super();
		this.utils = utils;
		this.orderDAO = orderDAO;
		this.customers = customerController;
	}

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Creates a new order that is tied to an existing customer
	 */

	@Override
	public Order create() {
		// First we need to list all customers
		List<Customer> customers = this.customers.readAll();
		customers.stream().forEach(customer -> LOGGER.info(customer));
		
		return null;
	}

	@Override
	public Order update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
