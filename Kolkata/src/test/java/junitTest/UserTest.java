package junitTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import user.User;

/**
 * 
 *
 */
public class UserTest {

	private User user;
	
	@Before
	public void setUp() {
		user = new User("123456789", "Thomas", "Muller");
	}
	
	@Test
	public void setName() {
		user.setFirstName("Arjen");
		user.setLastName("");
		assertEquals("Arjen", user.getFirstname());

		user.setFirstName("");
		user.setLastName("Robben");
		assertEquals("Robben", user.getLastname());

		user.setFirstName("Margarete");
		user.setLastName("Queen");
		assertEquals("Margarete", user.getFirstname());
		assertEquals("Queen", user.getLastname());
	}
}
