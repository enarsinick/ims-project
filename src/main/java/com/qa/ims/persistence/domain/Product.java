package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Product {
	
	private Long productId;
	private String title;
	private double price;
	
	public Product(Long productId, String title, double price) {
		super();
		this.productId = productId;
		this.title = title;
		this.price = price;
	}

	public Product(String title, double price) {
		super();
		this.title = title;
		this.price = price;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Product ID: " + productId + " | Title: " + title + " | Price: £" + price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(price, productId, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(productId, other.productId) && Objects.equals(title, other.title);
	}
	
}
