package com.wallet_jdbc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.wallet_jdbc.bean.Account;
import com.wallet_jdbc.bean.Customer;
import com.wallet_jdbc.bean.Transaction;
import com.wallet_jdbc.exception.WalletException;
import com.wallet_jdbc.service.WalletService;
import com.wallet_jdbc.service.WalletServiceImpl;

public class App 
{
	WalletService ws = new WalletServiceImpl();
	Scanner input = new Scanner(System.in);
	
    public static void main( String[] args )
    {
    
    	App A = new App();
       
    	while(true) {
    		System.out.println("===========SCB=================");
    		System.out.println("1) Create Account");
    		System.out.println("2) Show Balance");
    		System.out.println("3) Deposit");
    		System.out.println("4) Withdraw");
    		System.out.println("5) Fund Transfer");
    		System.out.println("6) Print Transaction");
    		System.out.println("7) Exit");
    		System.out.println("Enter option: ");
    		
    		String option = A.input.nextLine();
    		
    		switch (option) {
			case "1": A.createAccount();
				break;
			case "2": A.showBalance();
				break;
			case "3": A.deposit();
				break;
			case "4": A.withdraw();
				break;
			case "5": A.fundTransfer();
				break;
			case "6": A.printTransaction();
				break;
			case "7":
				System.out.println("Thank You!");
				System.exit(0);
				break;
			default:
				System.out.println("Please select options from 1-7.");
				break;
			}
    	}  
    }
    
    public void createAccount(){
    	
    	Customer c = new Customer();
    	Account a = new Account();
    	
    	System.out.println("Enter name: ");
    	c.setCustomer_name(input.nextLine());
    	
    	System.out.println("Enter Address: ");
    	c.setCustomer_address(input.nextLine());
    	
    	System.out.println("Enter age: ");
    	c.setCustomer_age(input.nextLine());
    	
    	System.out.println("Enter phone number: ");
    	c.setCustomer_phone(input.nextLine());
    	
    	System.out.println("Set a password for your account: ");
    	c.setCustomer_password(input.nextLine());
    	
    	System.out.println("Enter account type: ");
    	a.setAccount_type(input.nextLine());
    	
    	System.out.println("Enter initial amount deposited: ");
    	a.setAccount_balance(input.nextLine());
    	
    	try {
		   boolean ok = ws.validateData(c,a);
		   if(ok == true) {
			   String acc = ws.createAccount(c,a);
			  if(acc != "" && acc!=null) {
				  System.out.println(acc);
				  
				  String transId = ws.dwTransaction("success", "deposit", acc, LocalDate.now(), a.getAccount_balance());
				  System.out.println("Transaction Id: "+transId);
			  }
			  else {
				  System.out.println();
				  System.err.println("Some error in creating account.");
				  System.out.println();
			  }
		   }
		   else {
			   throw new WalletException("Error in the data provided.");
		   }
		} catch (WalletException e) {
				System.out.println();
				System.err.println("Error: "+e.getMessage());
				System.out.println();
		}
    	
    }
    
    public void showBalance() {
    	System.out.println("Enter account number: ");
    	String acc = input.nextLine();
    	
    	try {
    		String bal = ws.showBalance(acc);
			if(bal != null) {
				System.out.println("Balance available for account number "+acc+ " is "+ bal);
			}
			else {
				System.out.println("Account number "+acc + " not found in database.");
			}
		} catch (WalletException e) {
			System.out.println();
			System.err.println("Error: "+e.getMessage());
			System.out.println();
		}
    }
    
    public void deposit() {
    	
    	System.out.println("Enter account number: ");
    	String acc = input.nextLine();
    	System.out.println("Enter amount to be deposited: ");
    	String amt = input.nextLine();
    	
    	try {
			String totalAmt = ws.deposit(acc, amt);
			if(totalAmt == null) {
				System.err.println("Account not found in database.");
			}
			else {
				System.out.println("Amount "+amt+" deposited in account number "+ acc);
				System.out.println("New balance "+totalAmt);
				
				String transId = ws.dwTransaction("success", "deposit", acc, LocalDate.now(), amt);
				System.out.println("Transaction Id: "+transId);
			}
		} catch (WalletException e) {
			System.out.println();
			System.err.println("Error: "+e.getMessage());
			System.out.println();
		}
    	
    }
    
    public void withdraw() {
    	System.out.println("Enter account number: ");
    	String acc = input.nextLine();
    	System.out.println("Enter amount to be withdrawn: ");
    	String amt = input.nextLine();
    	
    	try {
    		String totalAmt = ws.withdraw(acc, amt);
			if(totalAmt == null) {
				System.err.println("Account not found in database.");
			}
			else {
				System.out.println("Amount "+amt+" withdrawn from account number "+ acc);
				System.out.println("New balance "+totalAmt);
				
				String transId = ws.dwTransaction("success", "whitdrawal", acc, LocalDate.now(), amt);
				System.out.println("Transaction Id: "+transId);
				
			}
		} catch (WalletException e) {
			System.out.println();
			System.err.println("Error: "+e.getMessage());
			System.out.println();
		}
    }
    
    public void fundTransfer() {
    	System.out.println("Enter your account number: ");
    	String acc = input.nextLine();
    	
    	System.out.println("Enter amount to be transferred: ");
    	String amt = input.nextLine();
    	
    	System.out.println("Enter receivers account number: ");
    	String rAcc = input.nextLine();
    	
    	try {
			 String balance = ws.fundTransfer(acc, amt, rAcc);
			
				 System.out.println("Amount "+ amt + " transferred to account number "+rAcc +" successfully");
				 System.out.println("New balance: "+ balance);
				 
				 String transId = ws.transferTransaction("success", "transfer", acc, rAcc, LocalDate.now(), amt);
				 System.out.println("Transaction ID: "+transId);
			 
		} catch (WalletException e) {
			System.out.println();
			System.err.println(e.getMessage());
			System.out.println();
		}
    }
    
    public void printTransaction() {
    	System.out.println("Enter account number: ");
    	String acc = input.nextLine();
    	
    	try {
			  boolean check = ws.checkAccountExist(acc);
			  if(check == true) {
				  System.out.println("Transaction History\n======================================"); 
				  
				  HashMap<String,Transaction> tMap = ws.printTransaction(acc);
				  
				  if(tMap.size() == 0) {
					  System.out.println("No transaction available");
				  }
				  else {
				    for(Map.Entry<String, Transaction> entry: tMap.entrySet()) {
					 
				      System.out.println("Transaction ID         : "+ entry.getKey().toString());
					  System.out.println("Status                 : "+ entry.getValue().getTransaction_status().toString());
					  System.out.println("Type                   : "+ entry.getValue().getTransaction_type().toString());
					  System.out.println("Amount                 : "+ entry.getValue().getTransaction_amount().toString());
					 if(entry.getValue().getTransaction_type().toString().equals("transfer")) {
					  System.out.println("Sender Account number  : "+ entry.getValue().getTransaction_sender().toString());
					  System.out.println("Receiver Account number: "+ entry.getValue().getTransaction_receiver().toString());
				   }
					 else if(ent){
					  System.out.println("Account number         : "+ entry.getValue().getAccount().toString());
					 }
					 System.out.println("======================================");
					 }
				  }
			  }
			  else {
				  System.err.println("Account number doesn't exists.");
			  }
		} catch (WalletException e) {
			System.err.println(e.getMessage());
		}
    }
    
    
  
}
