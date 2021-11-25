package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CustomerTest {
	
//	@Test
//	public void testEquals() {
//		EqualsVerifier.simple().forClass(Customer.class).verify();
//	}
	
	@Test
	public void testGetId() {
		Customer customer = new Customer("Nick", "Cave");
		customer.setId(1L);
		Long expected = 1L;
		Long result = customer.getId();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetFirstname() {
		Customer customer = new Customer("Nick", "Cave");
		String expected = "Nick";
		String result = customer.getFirstName();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetLastName() {
		Customer customer = new Customer("Nick", "Cave");
		String expected = "Cave";
		String result = customer.getLastName();
		assertEquals(expected, result);
	}
	
	@Test
	public void testToString() {
		Customer customer = new Customer(1L, "Nick", "Cave");
		String expected = "Customer ID: 1 | First Name: Nick | Last Name: Cave";
		String result = customer.toString();
		assertEquals(expected, result);
	}
	
	@Test
	public void testEquals() {
		Customer customer1 = new Customer(1L, "Nick", "Cave");
		Customer customer2 = new Customer(1L, "Nick", "Cave");
		assertTrue(customer1.equals(customer2));
	}
	
	@Test
	public void testHash() {
		Customer customer1 = new Customer(1L, "Nick", "Cave");
		Customer customer2 = new Customer(1L, "Nick", "Cave");
		assertEquals(customer1.hashCode(), customer2.hashCode());
	}
	
}
