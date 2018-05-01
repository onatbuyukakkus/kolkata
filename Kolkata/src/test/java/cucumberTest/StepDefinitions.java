/**
 * 
 */
package cucumberTest;

import static org.junit.Assert.assertTrue;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import user.User;

/**
 * 
 *
 */
public class StepDefinitions {
	
	public User testUser = null;
	
	@Given("^I am a user$")
	public void checkUser() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		this.testUser = new User("123456789", "Thomas", "Muller");
	}

	@When("^I create a barcode$")
	public void checkCreateBarcode() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		testUser.createNewBarcode();
	}

	@Then("^I get a barcode$")
	public void checkBarcode() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue("Barcode is created", testUser.getBarcode() != null);
	}
	
	/* Storage related */
	private String input;
	private String[] result;
	
	@Given("^A message is received$")
	public void a_message_is_received() throws Throwable {
		input  = "addUser/120211-3455/Alan/Turing";
	}
	
	@When("^The message is processed$")
	public void the_message_is_processed() throws Throwable {
		result = input.split("/");
	}
	
	@Then("^The message type exists$")
	public void the_message_type_exists() throws Throwable {
		assertTrue(result[0].equals("addUser") || result[0].equals("transaction"));
	}


}
