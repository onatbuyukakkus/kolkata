/**
 * 
 */
package cucumberTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.rpc.ServiceException;

import core.MerchantSimulator;
import core.MobileSimulator;
import core.Transaction;
import core.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 *
 */
public class TransferStepDefinitions {
	
	MerchantSimulator myMerchantSimulator;
	MobileSimulator myMobileSimulator;
	
	Helper myHelper;
	User user;
	Transaction transaction;
	
	String cvr;
	String cpr, firstName, lastName, barcode;
	String response;
	
	public TransferStepDefinitions() throws IOException, ServiceException {
		myMerchantSimulator = new MerchantSimulator();
		myMobileSimulator = new MobileSimulator();
	    myHelper = new Helper();
		
	    cpr = "696969-6969";
		firstName = "Gabriel";
		lastName = "Angel";
		
	    user = new User(cpr,firstName,lastName);
	}
	
	@Given("^I read an invalid barcode \"([^\"]*)\"$")
	public void i_read_an_invalid_barcode(String arg1) throws Exception {
	    barcode = arg1;
	}
	
	@When("^I make a transaction with my CVR number \"([^\"]*)\"$")
	public void i_make_a_transaction_with_my_CVR_number(String arg1) throws Exception {
		transaction = new Transaction("100",cpr,barcode);
		response = myMerchantSimulator.transaction(transaction).getBody();
	}

	@Then("^I get an invalid barcode response$")
	public void i_get_an_invalid_barcode_response() throws Exception {
		assertEquals("Invalid barcode!", response);
	}
	
	@Given("^I read a valid barcode$")
	public void i_read_a_valid_barcode() throws Exception {
		myHelper.addUserToBank(cpr, firstName, lastName);
		myMobileSimulator.addUser(user);
		barcode = myMobileSimulator.getBarcode(cpr);
	}

	@Then("^I get a transaction successful response$")
	public void i_get_a_transaction_successful_response() throws Exception {
		assertEquals("Transaction successful.", response);
	}

	@When("^I make a transaction with my CVR number \"([^\"]*)\" and high balance \"([^\"]*)\"$")
	public void i_make_a_transaction_with_my_CVR_number_and_high_balance(String arg1, String arg2) throws Exception {
		transaction = new Transaction(arg2,arg1,barcode);
		response = myMerchantSimulator.transaction(transaction).getBody();
	}

	@Then("^I get a balance will be negative response$")
	public void i_get_a_balance_will_be_negative_response() throws Exception {
		assertEquals("Debtor balance will be negative", response);
	}

}
