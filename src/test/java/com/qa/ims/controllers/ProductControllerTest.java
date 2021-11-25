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

import com.qa.ims.controller.ProductController;
import com.qa.ims.persistence.dao.ProductDAO;
import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	@Mock
	private Utils utils;
	
	@Mock
	private ProductDAO dao;
	
	@InjectMocks
	private ProductController controller;
	
	@Test
	public void testReadAll() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(1L, "Rome Total War", 19.99));
		
		Mockito.when(dao.readAll()).thenReturn(products);
		
		assertEquals(products, controller.readAll());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testCreate() {
		final String P_NAME = "Space Invaders";
		final double P_PRICE = 1.99D;
		final Product product = new Product(P_NAME, P_PRICE);
		
		Mockito.when(utils.getString()).thenReturn(P_NAME);
		Mockito.when(utils.getDouble()).thenReturn(P_PRICE);
		Mockito.when(dao.create(product)).thenReturn(product);
		
		assertEquals(product, controller.create());
		
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).create(product);
	}
	
	@Test
	public void testUpdate() {
		final String P_NAME = "Space Invaders";
		final double P_PRICE = 1.99D;
		final Long P_ID = 1L;
		final Product product = new Product(P_ID, P_NAME, P_PRICE);
		
		Mockito.when(utils.getLong()).thenReturn(P_ID);
		Mockito.when(utils.getString()).thenReturn(P_NAME);
		Mockito.when(utils.getDouble()).thenReturn(P_PRICE);
		Mockito.when(dao.update(product)).thenReturn(product);
		
		assertEquals(product, controller.update());
		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).update(product);
	}
	
	@Test
	public void testDelete() {
		final Long P_ID = 1L;
		
		Mockito.when(utils.getLong()).thenReturn(P_ID);
		Mockito.when(dao.delete(P_ID)).thenReturn(1);
		
		assertEquals(1L, controller.delete());
		
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(P_ID);
	}

}














