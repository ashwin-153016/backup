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
	public HashMap<String,String> createAccount(Customer c, Account a) throws WalletException {
		
		Connection connection = DBConnection.getInstance().getConnection();	
		Connection  connectionAcc = DBConnection.getInstance().getConnection();
		
		PreparedStatement preparedStatement=null;		
		//ResultSet resultSet = null;
		
		PreparedStatement preparedStatementAcc=null;		
		//ResultSet resultSetAcc = null;
		
		//String customerId="";
		//String accountId = "";
		
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
		
			//resultSet = preparedStatement.executeQuery();

			
			preparedStatementAcc = connection.prepareStatement(QueryMapper.INSERT_ACCOUNT);
			
			AccountNumber aNum = new AccountNumber();
			
			preparedStatementAcc.setString(1, aNum.getAccount_Number());
			preparedStatementAcc.setString(2, cId.getCustomer_id());
			preparedStatementAcc.setFloat(3, Float.valueOf(a.getAccount_balance()));
			preparedStatementAcc.setString(4, a.getAccount_type());
			
			queryResultAcc = preparedStatementAcc.executeUpdate();
			
		//	resultSetAcc = preparedStatementAcc.executeQuery();
			
			
			/*if(resultSet.next() && resultSetAcc.next())
			{
				//customerId=resultSet.getString(1).toString();
				//accountId=resultSetAcc.getString(1).toString();
			}*/
	
			if(queryResult==0 || queryResultAcc==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting customer details failed ");

			}
			else
			{
				HashMap<String,String> h = new HashMap<String,String>();
				//h.put(customerId, accountId);
				h.put(cId.getCustomer_id(), aNum.getAccount_Number());
				logger.info("Customer details added successfully:");
				return h;
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
		Connection connection_account= DBConnection.getInstance().getConnection();	
		
		PreparedStatement preparedStatement=null;
		
		
		ResultSet resultSet = null;
	
	
		Float balance=0f;
		
		int queryResult=0;
		
		try
		{		
			preparedStatement=connection_account.prepareStatement(QueryMapper.GET_BALANCE);
			
			preparedStatement.setString(1, accNumber);
			
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			
			if(resultSet.next())
			{
				balance = Float.valueOf(resultSet.getString(1));
			}
	
			if(queryResult==0)
			{
				logger.error("Getting Balance error ");
				throw new WalletException("Getting balance details failed ");

			}
			else
			{
				logger.info("Balance got successfully:");
				return balance.toString();
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
				connection_account.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}

	@Override
	public String deposit(String acc, String amt) throws WalletException {
		
		Connection connection_account= DBConnection.getInstance().getConnection();	
		
		PreparedStatement preparedStatement=null;
		
		
		ResultSet resultSet = null;
	
	
		Float balance=0f;
		
		int queryResult=0;
		
		try
		{		
			preparedStatement=connection_account.prepareStatement(QueryMapper.GET_BALANCE);
			
			preparedStatement.setString(1, acc);
			
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			
			if(resultSet.next())
			{
				balance = Float.valueOf(resultSet.getString(1));
				
				preparedStatement=connection_account.prepareStatement(QueryMapper.UPDATE_BALANCE);
				
				balance+=(Float.valueOf(amt));
				
				preparedStatement.setFloat(1, Float.valueOf(amt));
				preparedStatement.setString(2, acc);
				
				queryResult = preparedStatement.executeUpdate();
			
				resultSet = preparedStatement.executeQuery();
			}
	
			if(queryResult==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
				return acc;
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
				connection_account.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}

	@Override
	public String withdraw(String acc, String amt) throws WalletException {
	Connection connection_account= DBConnection.getInstance().getConnection();	
		
		PreparedStatement preparedStatement=null;
		
		
		ResultSet resultSet = null;
	
	
		Float balance=0f;
		
		int queryResult=0;
		
		try
		{		
			preparedStatement=connection_account.prepareStatement(QueryMapper.GET_BALANCE);
			
			preparedStatement.setString(1, acc);
			
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			
			if(resultSet.next())
			{
				balance = Float.valueOf(resultSet.getString(1));
				
				preparedStatement=connection_account.prepareStatement(QueryMapper.UPDATE_BALANCE);
				
				balance-=(Float.valueOf(amt));
				
				preparedStatement.setFloat(1, Float.valueOf(amt));
				preparedStatement.setString(2, acc);
				
				queryResult = preparedStatement.executeUpdate();
			
				resultSet = preparedStatement.executeQuery();
			}
	
			if(queryResult==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
				return acc;
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
				connection_account.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}

	@Override
	public boolean checkAccountExist(String acc) throws WalletException {
        Connection connection_account= DBConnection.getInstance().getConnection();	
		
		PreparedStatement preparedStatement=null;
				
		ResultSet resultSet = null;
		
		int queryResult=0;
		String acct="";
		
		try
		{		
			preparedStatement=connection_account.prepareStatement(QueryMapper.GET_ACCOUNT);
			
			preparedStatement.setString(1, acc);
			
		    queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			
			if(resultSet.next())
			{
				acct = resultSet.getString(1);
				
			}
	
			if(queryResult==0)
			{
				logger.error("No account found");
				return false;
			}
			else
			{
				logger.info("Account found");
				return true;
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
				connection_account.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}

	@Override
	public String fundTransfer(String acc, String amt, String rAcc) throws WalletException {

		Connection connection_account1= DBConnection.getInstance().getConnection();	
		Connection connection_account2=DBConnection.getInstance().getConnection();
		
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement2=null;
		
		ResultSet resultSet = null;
		ResultSet resultSet2=null;
	
	
		Float balance=0f;
		Float balance1=0f;
		
		int queryResult=0;
		int queryResult1 = 0;
		
		
		try
		{		
			preparedStatement=connection_account1.prepareStatement(QueryMapper.GET_BALANCE);
			preparedStatement2=connection_account2.prepareStatement(QueryMapper.GET_BALANCE);
			
			preparedStatement.setString(1, acc);
			preparedStatement2.setString(1, rAcc);
			
			queryResult = preparedStatement.executeUpdate();
			queryResult1 = preparedStatement2.executeUpdate();
			
			resultSet = preparedStatement.executeQuery();
			resultSet2 = preparedStatement2.executeQuery();
			
			if(resultSet.next())
			{
				balance = Float.valueOf(resultSet.getString(1));
				balance1 = Float.valueOf(resultSet2.getString(1));
				
				preparedStatement=connection_account1.prepareStatement(QueryMapper.UPDATE_BALANCE);
				preparedStatement2=connection_account2.prepareStatement(QueryMapper.UPDATE_BALANCE);
				
				balance+=(Float.valueOf(amt));
				balance1-=(Float.valueOf(amt));
				
				preparedStatement.setFloat(1, balance);
				preparedStatement.setString(2, acc);

				preparedStatement2.setFloat(1, balance1);
				preparedStatement2.setString(2, rAcc);
				
				queryResult = preparedStatement.executeUpdate();
				queryResult1 = preparedStatement2.executeUpdate();
				
				resultSet = preparedStatement.executeQuery();
				resultSet2 = preparedStatement2.executeQuery();
			}
	
			if(queryResult==0 || queryResult1==0)
			{
				logger.error("Updataion failed ");
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
				return balance.toString();
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
				preparedStatement2.close();
				
				connection_account2.close();
				connection_account1.close();
				
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}
	
	@Override
	public String transferTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender, String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException {
		
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
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
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
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}

	@Override
	public HashMap<String, Transaction> printTransaction(String acc) throws WalletException {
		
		HashMap<String, Transaction> transaction = new HashMap<String, Transaction>();
		Connection connection = DBConnection.getInstance().getConnection();	
		
		PreparedStatement preparedStatement=null;		
		ResultSet resultSet = null;
		
		String transactionId = "";
		Transaction t = new Transaction();
		
		int queryResult=0;
		
		try
		{		
			preparedStatement=connection.prepareStatement(QueryMapper.GET_TRANSACTION);
			    
			preparedStatement.setString(1, acc);
			preparedStatement.setString(2, acc);
			
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				transactionId = resultSet.getString(1);
				t.setTransaction_type(resultSet.getString(2));
				t.setTransaction_date(LocalDate.parse(resultSet.getString(3)));
				t.setTransaction_sender(resultSet.getString(4));
				t.setTransaction_receiver(resultSet.getString(5));
				t.setTransaction_status(resultSet.getString(6));
				t.setTransaction_amount(Float.toString(resultSet.getFloat(7)));
				
				transaction.put(transactionId, t);
				
			}
	
			if(queryResult==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
				return transaction;
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
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}

	}

	@Override
	public String dwTransaction(String transaction_type, LocalDate transaction_date, String transaction_sender,
			String transaction_receiver, String transaction_status, String transaction_amount) throws WalletException {
		

		Connection connection = DBConnection.getInstance().getConnection();	
				
		PreparedStatement preparedStatement=null;		
				
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
			preparedStatement.setFloat(7, Float.valueOf(transaction_amount));
			
			queryResult = preparedStatement.executeUpdate();
		
			/*resultSet = preparedStatement.executeQuery();*/

			/*if(resultSet.next())
			{
				transactionId=resultSet.getString(1);
			}*/
	
			if(queryResult==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
				return tId.getTransaction_Number();
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
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
		
	}

	@Override
	public String getCustomerId(String acc) throws WalletException {
		
		Connection connection = DBConnection.getInstance().getConnection();	
		
		PreparedStatement preparedStatement=null;		
		ResultSet resultSet = null;
		
		String customerId = "";
		
		int queryResult=0;
		
		try
		{		
			preparedStatement=connection.prepareStatement(QueryMapper.GET_CUSTOMER_ID);
			    
			preparedStatement.setString(1, acc);
			
			queryResult = preparedStatement.executeUpdate();
		
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next())
			{
				customerId=resultSet.getString(1);
			}
	
			if(queryResult==0)
			{
				logger.error("Insertion failed ");
				throw new WalletException("Inserting transaction details failed ");

			}
			else
			{
				logger.info("Transaction details added successfully:");
				return customerId;
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
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				logger.error(sqlException.getMessage());
				throw new WalletException("Error in closing db connection");	
			}
		}
	}

	

	
	

}
