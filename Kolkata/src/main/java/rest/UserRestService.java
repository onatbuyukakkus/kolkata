package rest;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import JMS.MessageProducerJms;
import user.User;

/**
 * Handle user rest services.
 * 
 */

@Stateless
@Path("/user")
public class UserRestService {	

	@Resource(mappedName = "java:jboss/exported/MyConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:jboss/exported/AddUserQueue")
	private Queue addUserQueue;
	
	@Resource(mappedName = "java:jboss/exported/GetBarcodeQueue")
	private Queue getBarcodeQueue;
	
	/**
	 * Add a user to DTUPay.
	 * @param user {@link User}
	 * @return Http response
	 */
	@POST
	@Consumes({ "application/json" })
	public Response addUser(User user) {
		String message = "addUser/" + user.getCpr() + "/" + user.getFirstname() + "/" + user.getLastname();
		if(user.getCpr() == null || user.getFirstname() == null || user.getLastname() == null) {
			return Response.status(400).entity("Missing parameters.").build();
		}
		try {
			String response = MessageProducerJms.sendMessage(message, connectionFactory, addUserQueue);
			return Response.status(200).entity(response).build();
		}
		catch(Exception e) {
			return Response.serverError().build();
		}
	}
	
	/**
	 * Get a barcode for given user.
	 * @param cpr {@link String}
	 * @return Http respose
	 */
	@GET
	@Path("barcode/{cpr}")
	public Response getBarcode(@PathParam("cpr") String cpr) {
		String message = "getBarcode/" + cpr;
		if(cpr == null) {
			return Response.status(400).entity("Missing parameters.").build();
		}
		try {
			String response = MessageProducerJms.sendMessage(message, connectionFactory, getBarcodeQueue);
			return Response.status(200).entity(response).build();
		}
		catch(Exception e) {
			return Response.serverError().build();
		}
	}
}