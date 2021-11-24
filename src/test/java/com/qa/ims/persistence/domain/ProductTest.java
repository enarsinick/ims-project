package com.qa.ims.persistence.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ProductTest {
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Product.class).verify();
	}

}
