package junitTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.rpc.ServiceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bankService.BankConnection;
import dtu.ws.fastmoney.*;


/**
 * 
 */

public class TestWebService {
	
	// Generating users
	static User hawking = new User();
	static User lovelace = new User();
	static User hopper = new User();
	static User stroustrup = new User();

	static User[] users = new User[3];

	// Balances
	private static BigDecimal smallBalance = new BigDecimal(500);
	private static BigDecimal mediumBalance = new BigDecimal(2000);
	private static BigDecimal largeBalance = new BigDecimal(5000);
	
	private BankConnection bank;

	public TestWebService() throws ServiceException {
		hawking.setCprNumber("270260-3619");
		hawking.setFirstName("Stephen");
		hawking.setLastName("Hawking");
		
		lovelace.setCprNumber("170280-3614");
		lovelace.setFirstName("Ada");
		lovelace.setLastName("Lovelace");
		
		hopper.setCprNumber("300560-3618");
		hopper.setFirstName("Grace");
		hopper.setLastName("Hopper");
		
		stroustrup.setCprNumber("111150-5579");
		stroustrup.setFirstName("Bjarne");
		stroustrup.setLastName("Stroustrup");
	
		users[0] = hawking;
		users[1] = lovelace;
		users[2] = hopper;
		bank = new BankConnection();
	}

	@Before
	public void createAccounts() throws BankServiceException_Exception, RemoteException, ServiceException {
		bank.createAccountWithBalance(hawking, smallBalance);
		bank.createAccountWithBalance(lovelace, mediumBalance);
		bank.createAccountWithBalance(hopper, largeBalance);
	}

	@After
	public void deleteCreatedAccounts() throws BankServiceException_Exception, RemoteException, ServiceException {

		for (int i = 0; i < users.length; i++) {
			bank.retireAccount(bank.getAccountByCprNumber(users[i].getCprNumber()).getId());
		}
	}

	@Test
	// Checking that the balances in the users match the initial values
	public void testInitialAccountInformation() throws BankServiceException_Exception, RemoteException, ServiceException {

		assertEquals(bank.getAccountByCprNumber(hawking.getCprNumber()).getBalance(), smallBalance);
		assertEquals(bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance(), mediumBalance);
		assertEquals(bank.getAccountByCprNumber(hopper.getCprNumber()).getBalance(), largeBalance);
	}

	@Test
	// Checking that test users are added to the bank
	public void testFindUsersInBank() throws BankServiceException_Exception, RemoteException, ServiceException {

		ArrayList<String> list = new ArrayList<String>();
		for (AccountInfo a : bank.getAccounts()) {
			list.add(a.getAccountId());
		}

		for (User user : users) {
			assertEquals(list.contains(bank.getAccountByCprNumber(user.getCprNumber()).getId()), true);
		}
	}

	@Test
	public void testTransferMoneySuccess() throws BankServiceException_Exception, RemoteException, ServiceException {
		// Customer and merchant account identifiers
		String customer = bank.getAccountByCprNumber(hawking.getCprNumber()).getId();
		String merchant = bank.getAccountByCprNumber(lovelace.getCprNumber()).getId();

		// Amount to be transferred
		BigDecimal transferAmount = new BigDecimal(500);

		// Initial balances for customer and merchant
		BigDecimal customerBalance = bank.getAccountByCprNumber(hawking.getCprNumber()).getBalance();
		BigDecimal merchantBalance = bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance();

		// Transfer money from user to user2
		bank.transferMoneyFromTo(customer, merchant, transferAmount, "Bought Book: Math for Dummies");

		// Checking that the balances have been updated correctly
		assertEquals(customerBalance.subtract(transferAmount),
				bank.getAccountByCprNumber(hawking.getCprNumber()).getBalance());
		assertEquals(merchantBalance.add(transferAmount), bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance());
	}

	@Test
	public void testTransferBalanceTooLow() throws BankServiceException_Exception, RemoteException, ServiceException {
		// Customer and merchant account identifiers
		String customer = bank.getAccountByCprNumber(lovelace.getCprNumber()).getId();
		String merchant = bank.getAccountByCprNumber(hopper.getCprNumber()).getId();

		// Amount to be transferred
		BigDecimal transferAmount = new BigDecimal(3000);

		// Initial balances for customer and merchant
		BigDecimal customerBalance = bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance();
		BigDecimal merchantBalance = bank.getAccountByCprNumber(hopper.getCprNumber()).getBalance();

		// Catching errors when balance of user is too low
		try {
			bank.transferMoneyFromTo(customer, merchant, transferAmount, "Payed debt.");
		} catch (BankServiceException_Exception e) {
			assertEquals("Debtor balance will be negative", e.getMessage());
		}

		// Transferral should not be accepted and their respective amount stay the same
		assertEquals(customerBalance, bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance());
		assertEquals(merchantBalance, bank.getAccountByCprNumber(hopper.getCprNumber()).getBalance());
	}

	@Test
	public void testNegativeTransfer() throws RemoteException, ServiceException, BankServiceException_Exception {
		// Customer and merchant account identifiers
		String customer = bank.getAccountByCprNumber(lovelace.getCprNumber()).getId();
		String merchant = bank.getAccountByCprNumber(hawking.getCprNumber()).getId();

		// Amount to be transferred
		BigDecimal transferAmount = new BigDecimal(-450);

		// Initial balances for customer and merchant
		BigDecimal customerBalance = bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance();
		BigDecimal merchantBalance = bank.getAccountByCprNumber(hopper.getCprNumber()).getBalance();

		// Catching errors when transfer amount is negative
		try {
			bank.transferMoneyFromTo(customer, merchant, transferAmount, "Payed debt.");
		} catch (BankServiceException_Exception e) {
			assertEquals("Amount must be positive", e.getMessage());
		}
		// Transferral should not be accepted and their respective amount stay the same
		assertEquals(customerBalance, bank.getAccountByCprNumber(lovelace.getCprNumber()).getBalance());
		assertEquals(merchantBalance, bank.getAccountByCprNumber(hopper.getCprNumber()).getBalance());

	}

	@Test
	public void nonExistingAccount() throws RemoteException, ServiceException {
		try {
			bank.getAccountByCprNumber(stroustrup.getCprNumber()).getId();
		} catch (BankServiceException_Exception e) {
			assertEquals("Account does not exist", e.getMessage());
		}
	}

	@Test
	public void alreadyExistingAccount() throws RemoteException, ServiceException {
		try {
			bank.createAccountWithBalance(lovelace, smallBalance);
		} catch (BankServiceException_Exception e) {
			assertEquals("Account already exists", e.getMessage());
		}
	}

	@Test
	public void testCreateAccountWithNegativeBalance() throws RemoteException, ServiceException {
		BigDecimal negativeAmount = new BigDecimal(-3500);
		try {
			bank.createAccountWithBalance(stroustrup, negativeAmount);
		} catch (BankServiceException_Exception e) {
			assertEquals("Initial balance must be 0 or positive", e.getMessage());
		}
	}

}
