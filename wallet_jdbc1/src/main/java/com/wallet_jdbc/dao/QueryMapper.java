package com.wallet_jdbc.dao;

public class QueryMapper {

	public static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?)";
	public static final String INSERT_ACCOUNT = "INSERT INTO ACCOUNT VALUES(?,?,?,?)";
	public static final String INSERT_TRANSACTION = "INSERT INTO TRANSACTION VALUES(?,?,?,?,?,?,?)";
	
	public static final String GET_CUSTOMER_ID = "SELECT customer_id FROM ACCOUNT WHERE account_number=?";
	public static final String GET_BALANCE = "SELECT account_balance FROM ACCOUNT WHERE account_number=?";
	public static final String GET_ACCOUNT = "SELECT * FROM ACCOUNT WHERE account_number=?";
	public static final String GET_TRANSACTION = "SELECT * FROM TRANSACTION WHERE sender_account_number=? OR receiver_account_number=?";
	
	public static final String UPDATE_BALANCE="UPDATE ACCOUNT SET account_balance=? WHERE account_number=?";
	
	
}
