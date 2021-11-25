package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Product;
import com.qa.ims.utils.DBUtils;

public class ProductDAOTest {
	
	private final ProductDAO DAO = new ProductDAO();
	
	@Before 
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final Product product = new Product(2L,"Space Invaders", 29.99);
		assertEquals(product, DAO.create(product));
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Product(1L,"Halo Infinite", 54.99), DAO.readLatest());
	}
	
	@Test
	public void testReadAll() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(1L,"Halo Infinite", 54.99));
		assertEquals(products, DAO.readAll());
	}
	
	@Test
	public void testRead() {
		assertEquals(new Product(1L, "Halo Infinite", 54.99), DAO.read(1L));
	}
	
	@Test
	public void testUpdate() {
		final Product product = new Product(1L,"Gears of War", 39.99);
		assertEquals(product, DAO.update(product));
	}
	
	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}

}














