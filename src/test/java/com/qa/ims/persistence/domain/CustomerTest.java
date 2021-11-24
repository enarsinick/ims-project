package com.qa.ims.persistence.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CustomerTest {
	
//	private final Customer customer = new Customer("Nick", "Cave");

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Customer.class).verify();
	}
	
//	@Test
//	public void testGetFirstName() {
//		assertEquals("Nick", customer.getFirstName());
//	}

}
