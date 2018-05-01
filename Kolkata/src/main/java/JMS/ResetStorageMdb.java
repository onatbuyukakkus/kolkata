package JMS;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import core.Storage;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "java:jboss/exported/ResetStorageQueue"
				)
})
public class ResetStorageMdb extends MessageReceiver {

	/** 
	 * Handles received message from REST on form "messagetype" = reset.
	 * Resetting all DTU Pay storages (used for testing purposes).
	 * @param receivedInput {@link String}[]
	 * @return Response to REST service
	 */
	@Override
	public String parseMessage(String[] receivedInput) {
		Storage.clear();
		return "Storage cleared.";
	}  
}

