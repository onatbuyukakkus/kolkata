package cucumberTest;

import static org.junit.Assert.assertEquals;

import javax.xml.rpc.ServiceException;

import core.MobileSimulator;
import core.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 */
public class CreateUserStepDefinitions {
	
	String cpr;
	String firstName;
	String lastName;
	
	MobileSimulator mobSimulator;
	String response;
	
	Helper myHelper;
	User user;
	
	public CreateUserStepDefinitions() throws ServiceException {
		myHelper = new Helper();
		mobSimulator = new MobileSimulator();
	}
	
	@Given("^I have my CPR \"([^\"]*)\", my first name \"([^\"]*)\" and my last name \"([^\"]*)\"$")
	public void initilaizeUserVariables(String cpr, String firstname, String lastname) throws Exception {
	    this.cpr = cpr;
	    this.firstName = firstname;
	    this.lastName = lastname;
	    user = new User(cpr,firstName,lastName);
	}

	@When("^I create a user with an existing bank account$")
	public void createUserWithBankAccount() throws Exception {
		myHelper.addUserToBank(cpr, firstName, lastName);	
		response = mobSimulator.addUser(user).getBody();
	}
	
	@When("^I create a user without an existing bank account$")
	public void createUserWithoutBankAccount() throws Exception {
		mobSimulator = new MobileSimulator();
		response = mobSimulator.addUser(user).getBody();
	}
	
	@Then("^I get an user created response$")
	public void checkUserCreateConfirmation() throws Exception {
		assertEquals("New user created.", response);
	}
	
	@Then("^I get an user does not have a bank account response$")
	public void checkUserCreateRejectionByBank() throws Exception {
		assertEquals("Bank account does not exist with this CPR: " + cpr, response);
	}
	
	@Then("^I get a user already exists response$")
	public void checkUserAlreadyExist() throws Exception {
		assertEquals("User already exists.", response);
	}
}
