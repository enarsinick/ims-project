package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.ProductController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private Utils utils;
	
	@Mock
	private OrderDAO dao;
	
	@Mock
	private CustomerController customers;
	
	@Mock
	private ProductController products;
	
	@InjectMocks
	private OrderController controller;
	
	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L, "Nick", "Cave", 24.99));
		
		Mockito.when(dao.readAll()).thenReturn(orders);
		
		assertEquals(orders, controller.readAll());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}
	
	@Test
	public void testCreate() {
		final Long CUST_ID = 1L;
		final Long ORDER_ID = 1L;
		final Order order = new Order(CUST_ID);
		Map<Long, Long> chosenProds = new HashMap<>();
		chosenProds.put(1L, 1L);
		
		Mockito.when(utils.getLong()).thenReturn(CUST_ID);
		Mockito.when(dao.create(order)).thenReturn(order);
		
		assertEquals(order, controller.create());
		
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(dao, Mockito.times(1)).create(order);
	}
	
	@Test
	public void testUpdateAddToOrder() {
		final Long ORDER_ID = 1L;
		final Long CUST_ID = 1L;
		final String input = "ADD";
		final Order order1 = new Order(ORDER_ID, CUST_ID);
		final Order order2 = new Order(ORDER_ID, CUST_ID, 1L, 1L);
		final Order order3 = new Order(1L, 1L, 1L, 1L);
		List<Product> products = Arrays.asList(new Product(1L, "Halo Infinite", 54.99));
		
		Mockito.when(utils.getLong()).thenReturn(ORDER_ID);
		Mockito.when(dao.read(ORDER_ID)).thenReturn(order1);
		Mockito.when(dao.getOrderContents(ORDER_ID)).thenReturn(products);
		Mockito.when(utils.getString()).thenReturn(input);
		Mockito.when(dao.update(order2)).thenReturn(order2);
		
		assertEquals(order3, controller.update());
		
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(1)).update(order2);
	}
	
	@Test
	public void testUpdateDeleteFromOrder() {
		final Long ORDER_ID = 1L;
		final Long CUST_ID = 1L;
		final String input = "DELETE";
		
		final Order order1 = new Order(ORDER_ID, CUST_ID);
		final Order order2 = new Order(ORDER_ID, CUST_ID, 1L, 1L);
		final Order order3 = new Order(1L, 1L, 1L, 1L);
		
		List<Product> products = Arrays.asList(new Product(1L, "Halo Infinite", 54.99));
		
		Mockito.when(utils.getLong()).thenReturn(ORDER_ID);
		Mockito.when(dao.read(ORDER_ID)).thenReturn(order1);
		Mockito.when(dao.getOrderContents(ORDER_ID)).thenReturn(products);
		Mockito.when(utils.getString()).thenReturn(input);
		Mockito.when(dao.removeFromOrder(1L, CUST_ID, ORDER_ID)).thenReturn(true);
		
		assertEquals(order1, controller.update());
		
		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(1)).removeFromOrder(1L, CUST_ID, ORDER_ID);
	}
	
	@Test
	public void testUpdateNoInput() {
		final Long ORDER_ID = 1L;
		final Long CUST_ID = 1L;
		final String input = " ";
		
		final Order order1 = new Order(ORDER_ID, CUST_ID);
		
		List<Product> products = Arrays.asList(new Product(1L, "Halo Infinite", 54.99));
		
		Mockito.when(utils.getLong()).thenReturn(ORDER_ID);
		Mockito.when(dao.read(ORDER_ID)).thenReturn(order1);
		Mockito.when(dao.getOrderContents(ORDER_ID)).thenReturn(products);
		Mockito.when(utils.getString()).thenReturn(input);
		
		assertEquals(null, controller.update());
		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
	}
	 
	
}














