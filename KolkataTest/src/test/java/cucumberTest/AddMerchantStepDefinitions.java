package cucumberTest;

import static org.junit.Assert.assertEquals;

import javax.xml.rpc.ServiceException;

import core.Merchant;
import core.MerchantSimulator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 */
public class AddMerchantStepDefinitions {

	String response;
	
	String cvr;
	String companyName;
	
	MerchantSimulator myMerchantSimulator;
	Helper myHelper;
	
	Merchant merchant;
	
	public AddMerchantStepDefinitions() throws ServiceException {
		myMerchantSimulator = new MerchantSimulator();
		myHelper = new Helper();
	}

	@Given("^I have my CVR number \"([^\"]*)\" and my company name \"([^\"]*)\"$")
	public void i_have_my_CVR_number_and_my_company_name(String arg1, String arg2) throws Exception {
	    cvr = arg1;
	    companyName = arg2;
	    merchant = new Merchant(cvr,companyName);
	}
	
	@When("^I create my account with an existing bank account$")
	public void createMerchantAccountWithBankAccount() throws Exception {
		myHelper.addUserToBank(cvr, companyName, "Merchant");
		response = myMerchantSimulator.createMerchant(merchant).getBody();
	}
	
	@When("^I create my account without an existing bank account$")
	public void createMerchantAccountWithoutBankAccount() throws Exception {
		response = myMerchantSimulator.createMerchant(merchant).getBody();
	}

	@Then("^I get a merchant created response$")
	public void checkPositiveResponse() throws Exception {
		assertEquals("New merchant created.", response);
	}

	@Then("^I get a merchant already exists response$")
	public void checkMerchantAlreadyExists() throws Exception {
		assertEquals("Merchant already exists.", response);
	}
	
	@Then("^I get a merchant does not have a bank account response$")
	public void checkMerchantDoesNotHaveABankAccount() throws Exception {
		assertEquals("Bank account does not exist with this CVR: " + cvr, response);
	}

}
