package com.wallet_jdbc.test;

import org.junit.Ignore;
import org.junit.Test;

import com.wallet_jdbc.bean.Account;
import com.wallet_jdbc.bean.Customer;
import com.wallet_jdbc.dao.WalletDao;
import com.wallet_jdbc.dao.WalletDaoImpl;
import com.wallet_jdbc.exception.WalletException;
import com.wallet_jdbc.service.WalletService;
import com.wallet_jdbc.service.WalletServiceImpl;

import junit.framework.TestCase;

public class WalletDaoImplTest extends TestCase {

	WalletDao wd = new WalletDaoImpl();
	WalletService ws = new WalletServiceImpl();
	Account a = new Account();
	
	
	@Test
	public void testCreateAccount() {
		
		assertEquals(10, a.getAccDetailMap().size());
		try {
			String id = wd.createAccount(new Customer("Aakash","Kancheepuram","24","9584625142"), new Account("savings","25000"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		assertEquals(11, a.getAccDetailMap().size());
	}

	
	@Test
	public void testShowBalance() {
		try {
			assertEquals("85000",wd.showBalance("SCB1531121607164"));
		} catch (WalletException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void testDeposit() {
		try {
			String bal = new String(wd.deposit("SCB1531121607164", "5000"));
			assertEquals("90000.0",bal);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void testWithdraw() {
		try {
			String bal = new String(wd.withdraw("SCB1531121607170", "5000"));
			assertEquals("3000.0",bal);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void testFundTransfer() {
		try {
			String bal = new String(wd.fundTransfer("SCB1531121607192", "1000","SCB1531121607195"));
			assertEquals("44000.0",bal);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void testPrintTransaction() {
		try {
			assertEquals(0,wd.printTransaction("SCB1531121607189").size());
		} catch (WalletException e) {
			System.err.println(e.getMessage());
			}
	}

}
