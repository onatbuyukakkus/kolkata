package JMS;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;
import user.User;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "java:jboss/exported/TransactionQueue"
				)
})
public class TransactionMdb extends MessageReceiver {

	/** 
	 * Handles received message from REST on form "messagetype"/amount/from (user cpr)/to (merchant cvr)/barcode".
	 * Calls the message producer to check user's bank balance, then either make
	 * transaction or not according to response coming from bank.
	 * @param receivedInput {@link String}[]
	 * @return Response to the REST service
	 */
	@Override
	public String parseMessage(String[] receivedInput) {

		String cpr = Storage.getBarcodeStorage().get(receivedInput[3]);
		User currentUser = Storage.getUserStorage().get(cpr);

		if(currentUser == null) {
			return("Invalid barcode!");
		}

		try {
			String messageToBank    = "transfer/" + receivedInput[1] + "/" + receivedInput[2] + "/" + receivedInput[3];
			String responseFromBank = MessageProducerJms.sendMessage(messageToBank, connectionFactory, transferBankQueue);

			if(responseFromBank.equals("Transaction successful")) {
				return currentUser.createNewBarcode();
			}
			else {
				return(responseFromBank);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "JMS Error";
	}
}
