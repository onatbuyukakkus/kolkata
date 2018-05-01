package rest;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import JMS.MessageProducerJms;
import merchant.Merchant;

/**
 * Handling the message for adding a user to DTU Pay.
 * 
 */
@Stateless
@Path("/merchant")
public class MerchantRestService {	

	@Resource(mappedName = "java:jboss/exported/MyConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:jboss/exported/AddMerchantQueue")
	private Queue addMerchantQueue;
	
	/**
	 * Adding a merchant to DTU Pay.
	 * @param merchant {@link Merchant}
	 * @return Http response
	 */
	@POST
	@Consumes({ "application/json" })
	public Response addMerchant(Merchant merchant) {
		String message = "addMerchant/" + merchant.getCvr() + "/" + merchant.getName();
		if(merchant.getCvr() == null || merchant.getName() == null) {
			return Response.status(400).entity("Missing parameters.").build();
		}
		try {
			String response = MessageProducerJms.sendMessage(message, connectionFactory, addMerchantQueue);
			return Response.status(200).entity(response).build();
		}
		catch(Exception e) {
			return Response.serverError().build();
		}
	}
}