package bankService;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import dtu.ws.fastmoney.*;

/**
 * 
 *
 */
public class BankConnection {
	
	private dtu.ws.fastmoney.BankServiceService service;
	private dtu.ws.fastmoney.BankService port;
	
	public BankConnection() throws ServiceException {
		service = new dtu.ws.fastmoney.BankServiceService();
		port = service.getBankServicePort();
	}
	
		// Available bank service methods
		public String createAccountWithBalance(User user, BigDecimal balance)
				throws RemoteException, ServiceException, BankServiceException_Exception {
			return port.createAccountWithBalance(user, balance);
		}

		public Account getAccountByCprNumber(String cpr)
				throws RemoteException, ServiceException, BankServiceException_Exception {
			return port.getAccountByCprNumber(cpr);
		}

		// Method not used as the get account by CPR is a better option
		public Account getAccount(String identifier)
				throws RemoteException, ServiceException, BankServiceException_Exception {
			return port.getAccount(identifier);
		}

		public List<AccountInfo> getAccounts() throws RemoteException, ServiceException, BankServiceException_Exception {
			return port.getAccounts();
		}

		public void retireAccount(String identifier)
				throws RemoteException, ServiceException, BankServiceException_Exception {
			port.retireAccount(identifier);
		}

		public void transferMoneyFromTo(String userIdentifier, String merchantIdentifier, BigDecimal amount,
				String message) throws RemoteException, ServiceException, BankServiceException_Exception {
			port.transferMoneyFromTo(userIdentifier, merchantIdentifier, amount, message);
		}
}
