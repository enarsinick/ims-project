package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductTest {
	
//	@Test
//	public void testEquals() {
//		EqualsVerifier.simple().forClass(Product.class).verify();
//	}
	
	@Test
	public void testGetId() {
		Product product = new Product("Worms", 1.99);
		product.setProductId(1L);
		product.setTitle("Age of Empires");
		product.setPrice(19.99);
		Long expected = 1L;
		Long result = product.getProductId();
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetTitle() {
		Product product = new Product("Worms", 1.99);
		String expected = "Worms";
		String result = product.getTitle();
		assertEquals(expected, result);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetPrice() {
		Product product = new Product("Worms", 1.99);
		double expected = 1.99;
		double result = product.getPrice();
		assertEquals(expected, result);
	}
	
	@Test
	public void testToString() {
		Product product = new Product(1L,"Worms", 1.99);
		String expected = "ID: 1 | Title: Worms | Price: £1.99";
		String result = product.toString();
		assertEquals(expected, result);
	}
	
	@Test
	public void testEquals() {
		Product product1 = new Product(1L,"Worms", 1.99);
		Product product2 = new Product(1L,"Worms", 1.99);
		assertTrue(product1.equals(product2));
	}
	
	@Test
	public void testHash() {
		Product product1 = new Product(1L,"Worms", 1.99);
		Product product2 = new Product(1L,"Worms", 1.99);
		assertEquals(product1.hashCode(), product2.hashCode());
	}

}
