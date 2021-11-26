package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class DomainTest {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Test
	public void testCustomerName() {
		assertEquals("CUSTOMER", Domain.CUSTOMER.name());
	}
	
	@Test
	public void testProductName() {
		assertEquals("PRODUCT", Domain.PRODUCT.name());
	}
	
	@Test
	public void testOrderName() {
		assertEquals("ORDER", Domain.ORDER.name());
	}
	
	@Test
	public void testStopName() {
		assertEquals("STOP", Domain.STOP.name());
	}
	
	@Test
	public void testGetDescriptionCustomer() {
		assertEquals("CUSTOMER: Information about customers", Domain.CUSTOMER.getDescription());
	}
	
	@Test
	public void testGetDescriptionProduct() {
		assertEquals("PRODUCT: Individual products", Domain.PRODUCT.getDescription());
	}
	
	@Test
	public void testGetDescriptionOrder() {
		assertEquals("ORDER: Purchases of products", Domain.ORDER.getDescription());
	}
	
	@Test
	public void testGetDescriptionStop() {
		assertEquals("STOP: To close the application", Domain.STOP.getDescription());
	}
	
	
}










