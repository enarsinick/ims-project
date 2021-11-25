package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Customer {

	private Long customerId;
	private String firstName;
	private String lastName;

	public Customer(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public Customer(Long customerId, String firstName, String lastName) {
		this.setId(customerId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public Long getId() {
		return customerId;
	}

	public void setId(Long customerId) {
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

	@Override
	public String toString() {
		return "Customer ID: " + customerId + " | First Name: " + firstName + " | Last Name: " + lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName);
	}	
	


}
