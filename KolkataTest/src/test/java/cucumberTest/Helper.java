package cucumberTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import bankService.BankConnection;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import utility.ResetStorage;

/**
 * 
 */

public class Helper {

	ResetStorage myTestSimulator;
	
	private BankConnection bank;
	static List<User> users  = new ArrayList<User>();
	
	public Helper() throws ServiceException{
		myTestSimulator = new ResetStorage();
		bank = new BankConnection();
	}
	
	@Before
	public void beforeScenario() throws IOException, ServiceException {
		myTestSimulator.resetStorage();
	}

	@After
	public void afterScenario() throws RemoteException, ServiceException, BankServiceException_Exception {
		retireBankAccounts();
	}
	
	public void addUserToBank(String id, String firstname, String lastname) throws RemoteException, ServiceException {
		User currentUser = new User();
		currentUser.setCprNumber(id);
		currentUser.setFirstName(firstname);
		currentUser.setLastName(lastname);
		users.add(currentUser);
		try {
			bank.createAccountWithBalance(currentUser, new BigDecimal(1000));
		} catch (BankServiceException_Exception e) {
			
		}
	}
	
	public void retireBankAccounts() throws RemoteException, ServiceException {
		for (User user : users) {
			try {
				bank.retireAccount(bank.getAccountByCprNumber(user.getCprNumber()).getId());
			} catch (BankServiceException_Exception e) {
			}
		}
		users.clear();
	}
	
}
