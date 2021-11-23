package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Order {

	private Long orderId;
	private Long customerId;
	private String firstName;
	private String lastName;
	private double total;
	
	public Order(Long orderId, String firstName, String lastName, double total) {
		this.orderId = orderId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.total = total;
	};
	
	public Order(Long orderId, Long customerId) {
		this.orderId = orderId;
		this.customerId = customerId;
	};
	
	public Order(Long customerId) {
		this.customerId = customerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ID: " + orderId + " | Name: " + firstName + " " + lastName + " | Total: £" + total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, firstName, lastName, orderId, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(orderId, other.orderId)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	
}
