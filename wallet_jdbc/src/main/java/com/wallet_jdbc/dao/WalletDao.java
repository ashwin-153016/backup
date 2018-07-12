package com.wallet_jdbc.dao;

import java.time.LocalDate;
import java.util.HashMap;

import com.wallet_jdbc.bean.Account;
import com.wallet_jdbc.bean.Customer;
import com.wallet_jdbc.bean.Transaction;
import com.wallet_jdbc.exception.WalletException;

public interface WalletDao {

	public String createAccount(Customer c, Account a) throws WalletException;
	public String showBalance(String accNumber) throws WalletException;
	public String deposit(String acc, String amt) throws WalletException;
	public String withdraw(String acc, String amt) throws WalletException;
	public boolean checkAccountExist(String acc) throws WalletException;
	public String fundTransfer(String acc, String amt, String rAcc) throws WalletException;

	public String dwTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender, String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException;
	public String transferTransaction(String status, String transaction_type, String senderAcc, String receiverAcc,LocalDate date, String amount) throws WalletException;

	public HashMap<String,Transaction> printTransaction(String acc) throws WalletException;
	
	
	
}
