package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
	
//	@Test
//	public void testCreate() {
//		final Long CUST_ID = 1L;
//		final Long P_ID = 1L;
//		final Long QUANT = 1L;
//		final Long ORDER_ID = 1L;
//		final Order order = new Order(ORDER_ID, CUST_ID);
//		Map<Long, Long> chosenProds = new HashMap<>();
//		chosenProds.put(1L, 1L);
//		
//		Mockito.when(utils.getLong()).thenReturn(CUST_ID);
//		Mockito.doNothing().when(controller).getProductChoice();
//		Mockito.when(dao.create(order)).thenReturn(order);
//		
//		assertEquals(order, controller.create());
//		
//		Mockito.verify(utils, Mockito.times(1)).getLong();
//		Mockito.verify(dao, Mockito.times(1)).create(order);
//	}
	
}














