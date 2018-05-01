package JMS;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.xml.rpc.ServiceException;

import bankService.BankConnection;
import core.Storage;
import dtu.ws.fastmoney.BankServiceException_Exception;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "java:jboss/exported/TransferBankQueue"
				)
})
public class TransferBankMdb extends MessageReceiver {

	private BankConnection bank;

	/**
	 * Handles the transaction of money from user to merchant.
	 * Creates a bank connection in order to make a transaction, returns result accordingly.
	 * @param receivedInput {@link String}[] containing amount, cvr number, bar code (which is translated into a cpr number)
	 * @return Message describing the final status of transaction. Possible object {@link String}
	 */
	@Override
	public String parseMessage(String[] receivedInput) {
		BigDecimal amount = new BigDecimal(receivedInput[1]);
		String cvr = receivedInput[2];
		String barcode = receivedInput[3];
		String cpr = Storage.getBarcodeStorage().get(barcode);
		String userId;
		String merchantId;

		try {
			bank = new BankConnection();
			try {
				userId     = bank.getAccountByCprNumber(cpr).getId();
				merchantId = bank.getAccountByCprNumber(cvr).getId();

				bank.transferMoneyFromTo(userId, merchantId, amount, amount + " transferred.");
				return("Transaction successful");
			} catch (BankServiceException_Exception e) {
				System.out.println(e.getMessage());
				return(e.getMessage());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "Web Service Error";
	}  
}
