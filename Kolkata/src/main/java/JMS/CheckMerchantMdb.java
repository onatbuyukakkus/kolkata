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
				propertyValue = "java:jboss/exported/CheckMerchantQueue"
				)
})

public class CheckMerchantMdb extends MessageReceiver {

	private BankConnection bank;

	/** 
	 * Handles received message from REST on form "messagetype"/CVR-number".
	 * Creates a bank connection in order to check merchant's bank account,
	 * returns result accordingly.
	 * @param receivedInput {@link String}[]
	 * @return Response from bank to JMS service
	 */
	@Override
	public String parseMessage(String[] receivedInput) {
		String cvr = receivedInput[1];
		Account merchantAccount;

		try {
			bank = new BankConnection();
			try {
				merchantAccount = bank.getAccountByCprNumber(cvr);
				if(merchantAccount != null) return "Account exists";
			} catch (BankServiceException_Exception e) {
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
