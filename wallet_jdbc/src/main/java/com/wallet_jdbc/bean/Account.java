package com.wallet_jdbc.bean;

public class Account {

	private String account_type;
	private String account_balance;
	
	
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(String account_balance) {
		this.account_balance = account_balance;
	}
	public Account(String account_type, String account_balance) {
		super();
		
		this.account_type = account_type;
		this.account_balance = account_balance;
	}
	public Account() {
		super();
	}
	
	
	
}
