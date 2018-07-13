package com.wallet_jdbc.bean;

public class TransactionId {
	
 private static String transaction_id;
	
	

	public  static String getTransaction_Number() {
		return transaction_id;
	}

	public  void setTransaction_Number(String account_number) {
		this.transaction_id = account_number;
	}

	public TransactionId(String transaction_number) {
		super();
		this.transaction_id = transaction_number;
	}

	public TransactionId() {
		super();
		this.transaction_id = "T"+System.currentTimeMillis();
	}
}
