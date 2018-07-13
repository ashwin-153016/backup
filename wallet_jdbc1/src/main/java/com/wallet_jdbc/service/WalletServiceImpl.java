package com.wallet_jdbc.service;

import java.time.LocalDate;
import java.util.HashMap;

import com.wallet_jdbc.bean.Account;
import com.wallet_jdbc.bean.Customer;
import com.wallet_jdbc.bean.Transaction;
import com.wallet_jdbc.dao.WalletDao;
import com.wallet_jdbc.dao.WalletDaoImpl;
import com.wallet_jdbc.exception.WalletException;

public class WalletServiceImpl implements WalletService {

	WalletDao wd = new WalletDaoImpl();
	
	@Override
	public HashMap<String,String> createAccount(Customer c, Account a) throws WalletException {
		return wd.createAccount(c,a);
	}

	@Override
	public boolean validateData(Customer c, Account a) throws WalletException {
		if(validateName(c.getCustomer_name()) && validateAge(c.getCustomer_age()) && validatePhone(c.getCustomer_phone()) && validateAccType(a.getAccount_type()) && validateInitBalance(a.getAccount_balance())) {
			return true;
		}
		return false;
	}
	
	private boolean validateName(String name) throws WalletException{
		if(name.isEmpty() || name==null) {
			throw new WalletException("Name cannot be empty.");
		}
		else {
			if(!name.matches("[A-Z][a-z]{3,}")) {
				throw new WalletException("Name should start with capital letter and contain minimum 3 characters.");
			}
		}
	 return true;
	}
	
	private boolean validateAge(String age) throws WalletException{
		if(age.isEmpty() || age==null) {
			throw new WalletException("Please enter age.");
		}
		else {
			int a;
			try {
				a = Integer.parseInt(age);
				
				if(a<=0) {
					throw new WalletException("Age cannot be less than 0"); 
				}
				else {
					return true;
				}
			} catch (Exception e) {
			    throw new WalletException("Age should be numerical.");	
			}
		}
	  
	}
	
		
	private boolean validatePhone(String phone) throws WalletException{
		
		if(phone.isEmpty() || phone==null) {
			throw new WalletException("Please enter phone number.");
		}
		else {
			 if(!phone.matches("\\d{10}")) {
				 throw new WalletException("Phone number should be 10 digits.");
			 }
		}
	return true;	
	}

	private boolean validateAccType(String type) throws WalletException{
		if(type.isEmpty() || type==null) {
			throw new WalletException("Account type cannot be empty.");
		}
		else {
			if(type.equalsIgnoreCase("savings")!=true && type.equalsIgnoreCase("current")!=true) {
				throw new WalletException("Account type can be only Savings or Current.");
			}
		}
	 return true;
	}
	
	private boolean validateInitBalance(String balance) throws WalletException{
		if(balance.isEmpty() || balance==null) {
			throw new WalletException("Please enter initial balance.");
		}
		else {
			double a;
			try {
				a = Double.parseDouble(balance);
				
				if(a<=0) {
					throw new WalletException("Balance cannot be less than 0"); 
				}
				else {
					return true;
				}
			} catch (Exception e) {
			    throw new WalletException("Balance should be numerical.");	
			}
		}
	  
	}

	@Override
	public String showBalance(String acc) throws WalletException {
		
		return wd.showBalance(acc);
	}

	@Override
	public String deposit(String acc, String amt) throws WalletException {
		try {
		Float d = Float.parseFloat(amt);
		if(d<0) {
			throw new WalletException("Amount should be greater than 0");
		}
		}catch (Exception e) {
			throw new WalletException("Enter valid amount.");
		}
		return wd.deposit(acc, amt);
	}

	@Override
	public String withdraw(String acc, String amt) throws WalletException {
		try {
			Float d = Float.parseFloat(amt);
			if(d<0) {
				throw new WalletException("Amount should be greater than 0");
			}
			else {
				String bal = wd.showBalance(acc);
				
				if(bal != null) {
					if(d > Double.parseDouble(bal)) {
						throw new WalletException("Withdrawal amount cannot be greater than balance amount.");
					}
					else {
						return wd.withdraw(acc, amt);
					}
				}
				else {
					return null;
				}
			}
			}catch (Exception e) {
				throw new WalletException(e.getMessage());
			}
		
	}

	@Override
	public boolean checkAccountExist(String acc) throws WalletException {
		
		return wd.checkAccountExist(acc);
	}

	@Override
	public String fundTransfer(String acc, String amt, String rAcc) throws WalletException {
		
		boolean sender = wd.checkAccountExist(acc);
		boolean receiver = wd.checkAccountExist(rAcc);
		
		if(sender == false) {
			throw new WalletException("Sender account doesn't exists.");
		}
		else if(receiver == false) {
			throw new WalletException("Receiver account doesn't exists.");
		}
		
		try {
			if(acc.equals(rAcc)) {
				throw new WalletException("Invalid transaction! Both account numbers same.");
			}
			Float amount = Float.parseFloat(amt);
			if(amount<0) {
				throw new WalletException("Amount cannot be less than 0");
			}
			if(amount > Float.parseFloat(wd.showBalance(acc))) {
				throw new WalletException("Insufficient balance in the account to complete this transaction.");
			}
			else {
				return wd.fundTransfer(acc, amt, rAcc);
			}
		} catch (WalletException e) {
			throw new WalletException(e.getMessage());
		}catch (Exception e) {
			throw new WalletException("Enter only numerical");
		}
	 
	}

	

	@Override
	public String transferTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender, String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException {
		return wd.transferTransaction(transaction_type, transaction_date, transaction_sender, transaction_receiver,transaction_status, transaction_amount);
	}

	@Override
	public HashMap<String, Transaction> printTransaction(String acc) throws WalletException {
	
		return wd.printTransaction(acc);
	}

	@Override
	public String dwTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender,
			String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException {
		
		return wd.dwTransaction(transaction_type, transaction_date, transaction_sender, transaction_receiver, transaction_status, transaction_amount);
	}

	@Override
	public String getCustomerId(String acc) throws WalletException {
		
		return wd.getCustomerId(acc);
	}

	
}
