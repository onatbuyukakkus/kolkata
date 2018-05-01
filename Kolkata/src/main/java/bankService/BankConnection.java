package bankService;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import dtu.ws.fastmoney.*;

/**
 * 
 */
public class BankConnection {

	private dtu.ws.fastmoney.BankServiceService service;
	private dtu.ws.fastmoney.BankService port;

	/**
	 * Constructor sets up the service and port for fastmoney bank service.
	 * @throws ServiceException
	 */
	public BankConnection() throws ServiceException {
		service = new dtu.ws.fastmoney.BankServiceService();
		port = service.getBankServicePort();
	}

	/**
	 * Creates an account on the bank with initial balance.
	 * @param user {@link User}
	 * @param balance {@link BigDecimal}
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws BankServiceException_Exception
	 */
	public String createAccountWithBalance(User user, BigDecimal balance)
			throws RemoteException, ServiceException, BankServiceException_Exception {
		return port.createAccountWithBalance(user, balance);
	}

	/**
	 * Get existing account in the bank by cpr number.
	 * @param cpr {@link String}
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws BankServiceException_Exception
	 * @return the account with given cpr {@link Account}
	 */
	public Account getAccountByCprNumber(String cpr)
			throws RemoteException, ServiceException, BankServiceException_Exception {
		return port.getAccountByCprNumber(cpr);
	}

	/**
	 * Get all accounts in the bank.
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws BankServiceException_Exception
	 * @return list of accounts in the bank {@link List} of {@link AccountInfo}
	 */
	public List<AccountInfo> getAccounts() throws RemoteException, ServiceException, BankServiceException_Exception {
		return port.getAccounts();
	}

	/**
	 * Removes an existing account in the bank.
	 * @param identifier {@link String}
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws BankServiceException_Exception
	 */
	public void retireAccount(String identifier)
			throws RemoteException, ServiceException, BankServiceException_Exception {
		port.retireAccount(identifier);
	}

	/**
	 * Transfer money from one account to another by an amount and with a message.
	 * @param userIdentifier {@link String}
	 * @param merchantIdentifier {@link String}
	 * @param amount {@link BigDecimal}
	 * @param message {@link String}
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws BankServiceException_Exception
	 */
	public void transferMoneyFromTo(String userIdentifier, String merchantIdentifier, BigDecimal amount, String message)
			throws RemoteException, ServiceException, BankServiceException_Exception {
		port.transferMoneyFromTo(userIdentifier, merchantIdentifier, amount, message);
	}
}
