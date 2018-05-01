package JMS;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "java:jboss/exported/AddMerchantQueue"
				)
})
public class AddMerchantMdb extends MessageReceiver {

	/** 
	 * Handles received message from REST on form "messagetype"/cvr-number/name".
	 * Calls the message producer to check merchant's bank account, then either add
	 * it to the storage or not according to response coming from bank.
	 * @param receivedInput {@link String}[]
	 * @return Response to the REST service
	 */
	@Override
	public String parseMessage(String[] receivedInput) {
		try {
			String messageToBank    = "checkAccount/" + receivedInput[1];
			String responseFromBank = MessageProducerJms.sendMessage(messageToBank, connectionFactory, checkMerchantQueue);
			
			if(responseFromBank.equals("Account does not exist")) {
				return("Bank account does not exist with this CVR: " + receivedInput[1]);
			}
			else if(responseFromBank.equals("Account exists")) {
				return Storage.addMerchant(receivedInput[1], receivedInput[2]);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "JMS Error";
	}    
}
