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
import transaction.Transaction;

/**
 * Handling received message for a transaction.
 * 
 */

@Stateless
@Path("/transaction")
public class TransactionRestService {	
	
	@Resource(mappedName = "java:jboss/exported/MyConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:jboss/exported/TransactionQueue")
	private Queue transactionQueue;
	
	/**
	 * Transfer money from a user to a merchant.
	 * @param transaction {@link Transaction}
	 * @return Http response
	 */
	@POST
	@Path("transfer")
	@Consumes({ "application/json" })
	public Response transfer(Transaction transaction) {
		String message = "transaction/" + transaction.getAmount() + "/" + transaction.getCvr() + "/" + transaction.getUUID();
		if(transaction.getAmount() == null || transaction.getCvr() == null || transaction.getUUID() == null) {
			return Response.status(400).entity("Missing parameters.").build();
		}
		try {
			String response = MessageProducerJms.sendMessage(message, connectionFactory, transactionQueue);
			return Response.status(200).entity(response).build();
        }
        catch(Exception ex) {
            return Response.serverError().build();
        }
	}
}