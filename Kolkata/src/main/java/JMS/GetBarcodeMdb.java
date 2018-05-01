package JMS;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import core.Storage;
import user.User;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "java:jboss/exported/GetBarcodeQueue"
				)
})
public class GetBarcodeMdb extends MessageReceiver {
	
	 /** 
	  * Handles received message from REST on form "messagetype"/cpr-number".
	  * Checks with the storage if user exists, if yes returns the barcode of the user
	  * if not, returns a warning text.
	  * @param receivedInput {@link String}[]
	  * @return Response to REST service
	  */
	@Override
	public String parseMessage(String[] receivedInput) {
		String cpr = receivedInput[1];
		User  user = Storage.getUserFromCpr(cpr);

		if(user == null) {
			return("Couldn't find user with the given CPR: " + cpr);
		}
		else {
			return user.getBarcode();
		}	
	}  
}