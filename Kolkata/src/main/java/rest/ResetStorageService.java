package rest;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import JMS.MessageProducerJms;

/**
 * Handling the message responsible for resetting all DTU Pay storages.
 * 
 */

@Stateless
@Path("/storage")
public class ResetStorageService {

	@Resource(mappedName = "java:jboss/exported/MyConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:jboss/exported/ResetStorageQueue")
	private Queue resetStorageQueue;

	/**
	 * Resets the storage in DTUPay
	 * @return Http response
	 */
	@POST
	@Path("reset")
	public Response resetStorage() {
		String message = "resetStorage/";
		try {
			String response = MessageProducerJms.sendMessage(message, connectionFactory, resetStorageQueue);
			return Response.status(200).entity(response).build();
		}
		catch(Exception e) {
			return Response.serverError().build();
		}
	}
}
