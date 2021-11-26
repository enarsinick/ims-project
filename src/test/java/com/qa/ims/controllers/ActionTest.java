package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.qa.ims.controller.Action;

public class ActionTest {
	
	@Test
	public void testCreateName() {
		assertEquals("CREATE", Action.CREATE.name());
	}
	
	@Test
	public void testReadName() {
		assertEquals("READ", Action.READ.name());
	}
	
	@Test
	public void testUpdateName() {
		assertEquals("UPDATE", Action.UPDATE.name());
	}
	
	@Test
	public void testDeleteName() {
		assertEquals("DELETE", Action.DELETE.name());
	}
	
	@Test
	public void testReturnName() {
		assertEquals("RETURN", Action.RETURN.name());
	}
	
	@Test
	public void testGetDescriptionCreate() {
		assertEquals("CREATE: To save a new entity into the database", Action.CREATE.getDescription());
	}
	
	@Test
	public void testGetDescriptionRead() {
		assertEquals("READ: To read an entity from the database", Action.READ.getDescription());
	}
	
	@Test
	public void testGetDescriptionUpdate() {
		assertEquals("UPDATE: To change an entity already in the database", Action.UPDATE.getDescription());
	}
	
	@Test
	public void testGetDescriptionDelete() {
		assertEquals("DELETE: To remove an entity from the database", Action.DELETE.getDescription());
	}
	
	@Test
	public void testGetDescriptionReturn() {
		assertEquals("RETURN: To return to domain selection", Action.RETURN.getDescription());
	}
	


}







