package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	
	private final OrderDAO DAO = new OrderDAO();
	
	@Before 
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final Order order = new Order(2L, 1L);
		assertEquals(order, DAO.create(order));
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Order(1L, 1L), DAO.readLatest());
	}
	
	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L,"jordan", "harrison", 54.99));
		assertEquals(orders, DAO.readAll());
	}
	
	@Test
	public void testGetOrderContents() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(1L, "Halo Infinite", 54.99));
		assertEquals(products, DAO.getOrderContents(1L));
	}
	
	@Test
	public void testCreateOrderItem() {
		boolean bool = DAO.createOrderItem(1L, 1L, 1L, 1L);
		assertTrue(bool);
	}
	
	@Test
	public void testRead() {
		assertEquals(new Order(1L, 1L), DAO.read(1L));
	}
	
	@Test
	public void testUpdate() {
		final Order order = new Order(1L, 1L, 1L, 1L);
		assertEquals(null, DAO.update(order));
	}
	
	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	@Test
	public void testRemoveFromOrder() {
		final Long PROD_ID = 1L;
		final Long CUST_ID = 1L;
		final Long ORDER_ID = 1L;
		assertTrue(DAO.removeFromOrder(PROD_ID, CUST_ID, ORDER_ID));
	}

}






