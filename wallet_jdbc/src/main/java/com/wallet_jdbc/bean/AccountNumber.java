package com.wallet_jdbc.bean;

public class AccountNumber {

	private static String account_number;
	
	

	public static String getAccount_Number() {
		return account_number;
	}

	public static void setAccount_Number(String account_number) {
		AccountNumber.account_number = account_number;
	}

	public AccountNumber(String account_number) {
		super();
		this.account_number = account_number;
	}

	public AccountNumber() {
		super();
		this.account_number = "SCB"+ System.currentTimeMillis();
	}
	
	
}
