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
				propertyValue = "java:jboss/exported/AddUserQueue"
				)
})

public class AddUserMdb extends MessageReceiver {

	/** 
	 * Handles received message from REST on form "messagetype"/cpr-number/first name/last name".
	 * Calls the message producer to check user's bank account, then either add
	 * it to the storage or not according to response coming from bank.
	 * @param receivedInput {@link String}[]
	 * @return Response to the REST service
	 */
	@Override
	public String parseMessage(String[] receivedInput) {
		try {
			String messageToBank    = "checkAccount/" + receivedInput[1];
			String responseFromBank = MessageProducerJms.sendMessage(messageToBank, connectionFactory, checkUserQueue);

			if(responseFromBank.equals("Account does not exist")) {
				return("Bank account does not exist with this CPR: " + receivedInput[1]);
			}
			else if(responseFromBank.equals("Account exists")) {
				return Storage.addUser(receivedInput);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "JMS Error";
	}  
}
