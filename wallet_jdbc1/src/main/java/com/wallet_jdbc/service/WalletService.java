package com.wallet_jdbc.service;

import java.time.LocalDate;
import java.util.HashMap;

import com.wallet_jdbc.bean.Account;
import com.wallet_jdbc.bean.Customer;
import com.wallet_jdbc.bean.Transaction;
import com.wallet_jdbc.exception.WalletException;

public interface WalletService {

	public HashMap<String,String> createAccount(Customer c, Account a) throws WalletException;
	public boolean validateData(Customer c, Account a) throws WalletException;
	public String showBalance(String acc) throws WalletException;
	public String deposit(String acc, String amt) throws WalletException;
	public String withdraw(String acc, String amt) throws WalletException;
	public String fundTransfer(String acc, String amt, String rAcc) throws WalletException;
	public boolean checkAccountExist(String acc) throws WalletException;
	
	public String getCustomerId(String acc)throws WalletException;
	public String dwTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender, String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException;
	public String transferTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender, String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException;
	
	
	public HashMap<String,Transaction> printTransaction(String acc) throws WalletException;
	
	
	
}
