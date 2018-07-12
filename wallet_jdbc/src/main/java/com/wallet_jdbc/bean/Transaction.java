package com.wallet_jdbc.bean;

import java.time.LocalDate;
import java.util.HashMap;

public class Transaction {

	private String transaction_status;
	private String transaction_type;
	private String transaction_sender;
	private String transaction_receiver;
	private LocalDate transaction_date;
	private String transaction_amount;
	public String getTransaction_status() {
		return transaction_status;
	}
	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public String getTransaction_sender() {
		return transaction_sender;
	}
	public void setTransaction_sender(String transaction_sender) {
		this.transaction_sender = transaction_sender;
	}
	public String getTransaction_receiver() {
		return transaction_receiver;
	}
	public void setTransaction_receiver(String transaction_receiver) {
		this.transaction_receiver = transaction_receiver;
	}
	public LocalDate getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}
	public String getTransaction_amount() {
		return transaction_amount;
	}
	public void setTransaction_amount(String transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	
	public Transaction(String transaction_status, String transaction_type, String transaction_sender,
			String transaction_receiver, LocalDate transaction_date, String transaction_amount) {
		super();
		this.transaction_status = transaction_status;
		this.transaction_type = transaction_type;
		this.transaction_sender = transaction_sender;
		this.transaction_receiver = transaction_receiver;
		this.transaction_date = transaction_date;
		this.transaction_amount = transaction_amount;
	}
	
	public Transaction() {
		super();
	}
	
	
	
	
	
	
}
