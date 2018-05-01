package JMS;

import java.rmi.RemoteException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.xml.rpc.ServiceException;

import bankService.BankConnection;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "java:jboss/exported/CheckUserQueue"
				)
})
public class CheckUserMdb extends MessageReceiver {

	private BankConnection bank;

	/**
	 * Handles received message from REST on form "messagetype"/cpr-number".
	 * Creates a bank connection in order to check user's bank account,
	 * returns result accordingly.
	 * @param receivedInput {@link String}[]
	 * @return Response from bank to JMS service
	 */
	@Override
	public String parseMessage(String[] receivedInput) {
		String cpr = receivedInput[1];
		Account userAccount;

		try {
			bank = new BankConnection();
			try {
				userAccount = bank.getAccountByCprNumber(cpr);
				if(userAccount != null) return "Account exists";
			} catch (BankServiceException_Exception e) {
				System.out.println(e.getMessage());
				return "Account does not exist"; 
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "Web Service Error";
	}  
}


