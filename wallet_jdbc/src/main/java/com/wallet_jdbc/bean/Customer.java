package com.wallet_jdbc.bean;

public class Customer {

	private String customer_name;
	private String customer_address;
	private String customer_age;
	private String customer_phone;
	private String customer_password;
	
	
	
	
	
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_address() {
		return customer_address;
	}
	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}
	public String getCustomer_age() {
		return customer_age;
	}
	public void setCustomer_age(String customer_age) {
		this.customer_age = customer_age;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getCustomer_password() {
		return customer_password;
	}
	public void setCustomer_password(String customer_password) {
		this.customer_password = customer_password;
	}
	public Customer(String customer_name, String customer_address, String customer_age,
			String customer_phone, String customer_password) {
		super();
		
		this.customer_name = customer_name;
		this.customer_address = customer_address;
		this.customer_age = customer_age;
		this.customer_phone = customer_phone;
		this.customer_password = customer_password;
	}
	public Customer() {
		super();
	}
	
	
	
	
	
	
}
