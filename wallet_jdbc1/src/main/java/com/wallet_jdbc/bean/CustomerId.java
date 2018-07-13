package com.wallet_jdbc.bean;

public class CustomerId {

	private String customer_id;

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public CustomerId(String customer_id) {
		super();
		this.customer_id = customer_id;
	}

	public CustomerId() {
		super();
		this.customer_id = "C"+System.currentTimeMillis();
	}
	
	
}
