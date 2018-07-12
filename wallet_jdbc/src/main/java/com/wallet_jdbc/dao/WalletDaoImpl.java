package com.wallet_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.wallet_jdbc.exception.*;
import com.wallet_jdbc.bean.Account;
import com.wallet_jdbc.bean.AccountNumber;
import com.wallet_jdbc.bean.Customer;
import com.wallet_jdbc.bean.CustomerId;
import com.wallet_jdbc.bean.Transaction;
import com.wallet_jdbc.bean.TransactionId;
import com.wallet_jdbc.exception.WalletException;
import com.wallet_jdbc.util.DBConnection;

public class WalletDaoImpl implements WalletDao {

Logger logger=Logger.getRootLogger();
	
	
	public WalletDaoImpl() {
		
		PropertyConfigurator.configure("resources//log4j.properties");
	}

	
	@Override
	public String createAccount(Customer c, Account a) throws WalletException {
		
		Connection connection = DBConnection.getInstance().getConnection();	
		Connection  connectionAcc = DBConnection.getInstance().getConnection();
		
		PreparedStatement preparedStatement=null;		
		ResultSet resultSet = null;
		
		PreparedStatement preparedStatementAcc=null;		
		ResultSet resultSetAcc = null;
		
		String customerId="";
		String accountId = "";
		
		int queryResult=0;
		int queryResultAcc =0;
		
		try
		{		
			preparedStatement=connection.prepareStatement(QueryMapper.INSERT_CUSTOMER);
			
		    CustomerId cId = new CustomerId();
		    
			preparedStatement.setString(1, cId.getCustomer_id());
			preparedStatement.setString(2, c.getCustomer_name());
			preparedStatement.setString(3, c.getCustomer_address());
			preparedStatement.setString(4, c.getCustomer_age());
			preparedStatement.setString(5, c.getCustomer_phone());
			preparedStatement.setString(6, c.getCustomer_password());
				
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			
			preparedStatementAcc = connection.prepareStatement(QueryMapper.INSERT_ACCOUNT);
			
			AccountNumber aNum = new AccountNumber();
			
			preparedStatementAcc.setString(1, aNum.getAccount_Number());
			preparedStatementAcc.setString(2, cId.getCustomer_id());
			preparedStatementAcc.setString(3, a.getAccount_balance());
			preparedStatementAcc.setString(4, a.getAccount_type());
			
			queryResultAcc = preparedStatementAcc.executeUpdate();
			
			resultSetAcc = preparedStatementAcc.executeQuery();
			
			
			if(resultSet.next() && resultSetAcc.next())
			{
				customerId=resultSet.getString(1);
				accountId=resultSetAcc.getString(1);
			}
	
			if(queryResult==0 && queryResultAcc==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting customer details failed ");

			}
			else
			{
				logger.info("Customer details added successfully:");
				return "Customer Id: "+customerId +"\nAccount number: "+accountId;
			}
			
			
			
		 }
		catch(SQLException sqlException)
		{
			logger.error(sqlException.getMessage());
			throw new WalletException("Technical problem occured refer log");
		}
		finally
		{
			try 
			{
				preparedStatement.close();
				preparedStatementAcc.close();
				connection.close();
				connectionAcc.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	
	}

	@Override
	public String showBalance(String accNumber) throws WalletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deposit(String acc, String amt) throws WalletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String withdraw(String acc, String amt) throws WalletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkAccountExist(String acc) throws WalletException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String fundTransfer(String acc, String amt, String rAcc) throws WalletException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public String transferTransaction(String status, String transaction_type, String senderAcc, String receiverAcc,
			LocalDate date, String amount) throws WalletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Transaction> printTransaction(String acc) throws WalletException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String dwTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender,
			String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException {
		

		Connection connection = DBConnection.getInstance().getConnection();	
				
		PreparedStatement preparedStatement=null;		
		ResultSet resultSet = null;
		
		String transactionId = "";
		
		int queryResult=0;
		
		try
		{		
			preparedStatement=connection.prepareStatement(QueryMapper.INSERT_TRANSACTION);
			
		    TransactionId tId = new TransactionId();
		    
			preparedStatement.setString(1, tId.getTransaction_Number());
			preparedStatement.setString(2, transaction_type);
			preparedStatement.setString(3, transaction_date.toString());
			preparedStatement.setString(4, transaction_sender);
			preparedStatement.setString(5, transaction_receiver);
			preparedStatement.setString(6, transaction_status);
			preparedStatement.setString(7, transaction_amount);
			
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				transactionId=resultSet.getString(1);
			}
	
			if(queryResult==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting customer details failed ");

			}
			else
			{
				logger.info("Customer details added successfully:");
				return transactionId;
			}
			
			
			
		 }
		catch(SQLException sqlException)
		{
			logger.error(sqlException.getMessage());
			throw new WalletException("Technical problem occured refer log");
		}
		finally
		{
			try 
			{
				preparedStatement.close();
				preparedStatementAcc.close();
				connection.close();
				connectionAcc.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
		
		return null;
	}

	

	
	

}
