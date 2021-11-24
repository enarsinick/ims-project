package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrderTest {

	@Test
	public void testGetId() {
		Order order = new Order(1L, "Nick", "Cave", 19.99);
		order.setOrderId(2L);
		order.setFirstName("Alan");
		order.setLastName("Jones");
		Long expected = 2L;
		Long result = order.getOrderId();
		assertEquals(expected, result);
	}

	@Test
	public void testGetCustomerId() {
		Order order = new Order(1L, 1L);
		Long expected = 2L;
		order.setCustomerId(2L);
		Long result = order.getCustomerId();
		assertEquals(expected, result);
	}

	@Test
	public void testGetFirstName() {
		Order order = new Order(1L, "Nick", "Cave", 19.99);
		String expected = "Nick";
		String result = order.getFirstName();
		assertEquals(expected, result);
	}

	@Test
	public void testGetLastName() {
		Order order = new Order(1L, "Nick", "Cave", 19.99);
		String expected = "Cave";
		String result = order.getLastName();
		assertEquals(expected, result);
	}

	@SuppressWarnings("deprecation")
	public void getTotal() {
		Order order = new Order(1L, "Nick", "Cave", 19.99);
		double expected = 19.99;
		double result = order.getTotal();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetProductId() {
		Order order = new Order(1L, 1L, 1L, 1L);
		Long expected = 2L;
		order.setProductId(2L);
		Long result = order.getProductId();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetQuantity() {
		Order order = new Order(1L, 1L, 1L, 1L);
		Long expected = 2L;
		order.setQuantity(2L);
		Long result = order.getQuantity();
		assertEquals(expected, result);
	}

	@Test
	public void testToString() {
		Order order = new Order(1L, "Nick", "Cave", 19.99);
		String expected = "ID: 1 | Name: Nick Cave | Total: £19.99";
		String result = order.toString();
		assertEquals(expected, result);
	}
	
	@Test
	public void testEquals() {
		Order order1 = new Order(1L, 1L, 1L, 1L);
		Order order2 = new Order(1L, 1L, 1L, 1L);
		assertTrue(order1.equals(order2));
	}
	
	@Test
	public void testHash() {
		Order order1 = new Order(1L);
		Order order2 = new Order(1L);
		assertEquals(order1.hashCode(), order2.hashCode());
	}

}
