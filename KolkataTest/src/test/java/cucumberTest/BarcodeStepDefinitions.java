package cucumberTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import core.MobileSimulator;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 */
public class BarcodeStepDefinitions {
	
	String noBarcode;
	String oldBarcode;
	String userBarcode;
	
	String cpr; 
	
	MobileSimulator mobSimulator;
		
	public BarcodeStepDefinitions() {
		mobSimulator = new MobileSimulator();
	}
	
	@When("^I request a new barcode with a cpr \"([^\"]*)\"$")
	public void i_request_a_new_barcode_with_a_cpr(String arg1) throws Exception {
		noBarcode = null;
		oldBarcode = UUID.randomUUID().toString();
		userBarcode = mobSimulator.getBarcode(arg1);
		cpr = arg1;
	}

	@Then("^I get my barcode$")
	public void acquireBarcodeCheck() throws Exception {
		assertNotEquals(oldBarcode, userBarcode);
		assertNotEquals(noBarcode, userBarcode);
	}
	
	@Then("^I get a couldn't find user response$")
	public void i_get_a_couldn_t_find_user_response() throws Exception {
	    assertEquals("Couldn't find user with the given CPR: " +  cpr, userBarcode);
	}
	
}
